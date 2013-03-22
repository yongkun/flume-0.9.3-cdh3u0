/**
 * Licensed to Cloudera, Inc. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  Cloudera, Inc. licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudera.flume.handlers.thrift;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.ArrayList;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudera.flume.conf.Context;
import com.cloudera.flume.conf.FlumeConfiguration;
import com.cloudera.flume.conf.SinkFactory.SinkBuilder;
import com.cloudera.flume.core.Event;
import com.cloudera.flume.core.EventImpl;
import com.cloudera.flume.core.EventSink;
import com.cloudera.flume.core.EventAck;
import com.cloudera.flume.handlers.endtoend.AckDistributor;
import com.cloudera.flume.handlers.thrift.ThriftFlumeEvent;
import com.cloudera.flume.handlers.thrift.RawEvent;
import com.cloudera.flume.handlers.thrift.ThriftFlumeEventServer.Client;
import com.cloudera.flume.handlers.thrift.ThriftAckReceiver;
import com.cloudera.flume.handlers.thrift.ThriftEventAck;
import com.cloudera.flume.handlers.thrift.ThriftEventAckAdaptor;
import com.cloudera.flume.handlers.thrift.EventStatus;
import com.cloudera.flume.reporter.ReportEvent;
import com.cloudera.flume.agent.FlumeNode;

import org.apache.commons.lang.NotImplementedException;

/**
 * This is a sink that sends events to a remote host/port using Thrift.
 */
public class ThriftEventSink extends EventSink.Base {

  static final Logger LOG = LoggerFactory.getLogger(ThriftEventSink.class);

  final public static String A_SERVERHOST = "serverHost";
  final public static String A_SERVERPORT = "serverPort";
  final public static String A_SENTBYTES = "sentBytes";

  String host;
  int port;  
  Client client;
  TTransport transport;
  TStatsTransport stats;
  boolean nonblocking;

  AtomicLong sentBytes = new AtomicLong();

  public ThriftEventSink(String host, int port, boolean nonblocking) {
    this.host = host;
    this.port = port;
    this.nonblocking = nonblocking;
  }

  public ThriftEventSink(String host, int port) {
    this(host, port, false);
  }

  @Override
  public void append(Event e) throws IOException, InterruptedException {
    ThriftFlumeEvent tfe = ThriftEventAdaptor.convert(e);
    try {
    	TStatsTransport tst = (TStatsTransport)(client.getOutputProtocol().getTransport());
        TSocket ts = (TSocket)(tst.getTransport());
        String hostName = ts.getSocket().getLocalAddress().getCanonicalHostName();
        String hostIP = ts.getSocket().getLocalAddress().getHostAddress();
        int localPort = ts.getSocket().getLocalPort();                
    	
	    List<String> hostList = tfe.getHostList();
	    if ( hostList == null ) {
	      hostList = new ArrayList<String>();
	    }
	    hostList.add(AckDistributor.getHostPortString(hostName, hostIP, localPort));
	    LOG.debug("Append " + 
	    	AckDistributor.getHostPortString(hostName, hostIP, localPort) + " to event.");
	    tfe.setHostList(hostList);

	  client.append(tfe);
      sentBytes.set(stats.getBytesWritten());
      super.append(e);
    } catch (TException e1) {
      throw new IOException("Append failed " + e1.getMessage(), e1);
    }
  }

  @Override
  public void close() throws IOException {
    if (transport != null) {
      transport.close();
      transport = null;
      LOG.info("ThriftEventSink on port " + port + " closed");
    }
  }

  @Override
  public void open() throws IOException {

    try {
      int timeout = FlumeConfiguration.get().getThriftSocketTimeoutMs();
      if (nonblocking) {
        // non blocking must use "Framed transport"
        transport = new TSocket(host, port, timeout);
        stats = new TStatsTransport(transport);
        transport = new TFramedTransport(stats);
      } else {
        transport = new TSocket(host, port, timeout);
        stats = new TStatsTransport(transport);
        transport = stats;
      }

      TProtocol protocol = new TBinaryProtocol(transport);
      transport.open();
      client = new Client(protocol);
      LOG.info("ThriftEventSink open on port " + port + " opened");

    // Get the ack, if its destination is this host, then send it to 
    // local wal manager, WALAckManager; if not, add it to the queue of 
    // ack distributor, AckDistributor.
    ThriftFlumeEventServer.Iface handler = new ThriftFlumeEventServer.Iface() {
      @Override
      public void append(ThriftFlumeEvent evt) throws TException {
        throw new NotImplementedException();
      }

      @Override
      public void rawAppend( RawEvent evt) throws TException {
        throw new NotImplementedException();
      }

      @Override
      public EventStatus ackedAppend( ThriftFlumeEvent evt) throws TException {
        throw new NotImplementedException();
      }

      @Override
      public void close() throws TException {
        throw new NotImplementedException();
      }
      
      @Override
      public void checkAck(ThriftEventAck tack) throws TException {
        LOG.debug("Get ack: " + tack.ackID);
        EventAck ack = ThriftEventAckAdaptor.convert(tack);
        if ( ack.isDestination() ) {
          LOG.info("Get my Ack: " + ack.ackID);
          FlumeNode.getInstance().getAckChecker().checkAck(ack.ackID);
        } else {
          LOG.info("Fwd Ack: " 
                + ack.ackID);
          FlumeNode.getInstance().getAckDistributor().addAck(ack);
        }
      }
    };
    FlumeNode.getInstance().setAckReceiver(new ThriftAckReceiver(protocol, handler));
    new Thread(FlumeNode.getInstance().getAckReceiver()).start();

    } catch (TTransportException e) {
      throw new IOException("Failed to open thrift event sink at " + host + ":"
          + port + " : " + e.getMessage());
    }
  }

  @Override
  public ReportEvent getMetrics() {
    ReportEvent rpt = super.getMetrics();
    rpt.setStringMetric(A_SERVERHOST, host);
    rpt.setLongMetric(A_SERVERPORT, port);
    rpt.setLongMetric(A_SENTBYTES, sentBytes.get());
    return rpt;
  }

  @Deprecated
  @Override
  public ReportEvent getReport() {
    ReportEvent rpt = super.getReport();
    rpt.setStringMetric(A_SERVERHOST, host);
    rpt.setLongMetric(A_SERVERPORT, port);
    rpt.setLongMetric(A_SENTBYTES, sentBytes.get());
    return rpt;
  }

  public static void main(String argv[]) {
    FlumeConfiguration conf = FlumeConfiguration.get();
    ThriftEventSink sink = new ThriftEventSink("localhost", conf
        .getCollectorPort());
    try {
      sink.open();

      for (int i = 0; i < 100; i++) {
        Event e = new EventImpl(("This is a test " + i).getBytes());
        sink.append(e);
        Thread.sleep(200);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public static SinkBuilder builder() {
    return new SinkBuilder() {
      @Override
      public EventSink build(Context context, String... args) {
        if (args.length > 2) {
          throw new IllegalArgumentException(
              "usage: thriftSink([hostname, [portno]]) ");
        }
        String host = FlumeConfiguration.get().getCollectorHost();
        int port = FlumeConfiguration.get().getCollectorPort();
        if (args.length >= 1) {
          host = args[0];
        }

        if (args.length >= 2) {
          port = Integer.parseInt(args[1]);
        }
        return new ThriftEventSink(host, port);
      }
    };
  }
}
