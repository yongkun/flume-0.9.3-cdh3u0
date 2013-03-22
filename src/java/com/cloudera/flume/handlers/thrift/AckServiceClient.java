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

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudera.flume.handlers.endtoend.ServiceClient;

/**
 * A wrapper for the connection. The connection is obtained when the Event 
 * connection is established.
 * This wrapper class will add more required information such as hostname and port
 * for ack distributor (AckDistributor) to send the ack back.  
 */
public class AckServiceClient implements ServiceClient {
  static final Logger LOG = LoggerFactory.getLogger(AckServiceClient.class);

  protected final TTransport transport;
  protected final String addr;
  protected final String hostName;
  protected final int port;
  protected final ThriftFlumeEventServer.Client client;

  public AckServiceClient(TTransport transport) {

    if ( ! transport.getClass().equals(TStatsTransport.class) ) {
    	String errMsg = String.format("Expecting %s wrapped in %s.", 
    			TStatsTransport.class.getName(), transport.getClass().getName()
    			);
    	LOG.error(errMsg);
    	throw new Error(errMsg);
    } 
    TStatsTransport strans = (TStatsTransport)transport;

    TTransport trans = strans.getTransport();

    if ( ! trans.getClass().equals(TSocket.class) ) {
    	String errMsg = String.format("Expecting %s wrapped in %s.", 
                TSocket.class.getName(), trans.getClass().getName()); 
      LOG.error(errMsg);
      throw new Error(errMsg);
    } 
    TSocket tsocket = (TSocket)trans;

    this.transport = transport;
    this.client = new ThriftFlumeEventServer.Client(new TBinaryProtocol(transport));
    this.addr = tsocket.getSocket().getInetAddress().getHostAddress();
    this.hostName = tsocket.getSocket().getInetAddress().getCanonicalHostName();
    this.port = tsocket.getSocket().getPort();
    
    int localPort = tsocket.getSocket().getLocalPort();
    LOG.info("Get new client. HostName: " + this.hostName + ", local port: " 
    		+ localPort + ", remote port: " + this.port);    
  }

  @Override
  public String getHostAddress() {
    return addr;
  }

  @Override
  public String getHostName() {
    return hostName;
  }
  
  @Override
  public int getPort() {
    return this.port;
  }  

  @Override
  public Object getClient() {	  
	  	return this.client;
  }
}

