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

package com.cloudera.flume.handlers.endtoend;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.cloudera.flume.conf.FlumeConfiguration;
import com.cloudera.flume.core.EventAck;
import com.cloudera.flume.handlers.endtoend.ServiceClient;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is an ack distributor thread, which will distribute the acks to its next
 * stop according to the host list in the ThriftEventAck. The connection is the
 * same connection used for the Event. The connections are stored in queue when
 * the connection is setup by the Event transmission.
 */
public class AckDistributor implements Runnable {
  private static final Logger LOG = LoggerFactory.getLogger(AckDistributor.class);

  protected final BlockingQueue<EventAck> ackQueue;
  
  /*
   * A map of "HostName:Port" -> ServiceClient
   */
  protected final Map<String, ServiceClient> clients;

  public AckDistributor() {
    this.ackQueue = new LinkedBlockingQueue<EventAck>();
    this.clients = new HashMap<String, ServiceClient>();
  }

  public void addClient(ServiceClient client) {	  
	String clientKey = getHostPortString(client.getHostName(), client.getHostAddress(), client.getPort());
    clients.put(clientKey, client);
    LOG.info("Enqueue connection " + clientKey + ", number of connections: " + clients.size());
  }
  
  @Override
  public void run() {
    throw new NotImplementedException();
  }

  public synchronized void addAck(EventAck ack) {
    ackQueue.add(ack);
  }
  public synchronized void addAckAll(List<EventAck> ack) {
    ackQueue.addAll(ack);
  }
  
  public static String getHostPortString(String hostName, String hostIP, int port) {
	  boolean useIP = FlumeConfiguration.get().getBoolean("ack.hostlist.using.ip", true);
	  return ( useIP ? hostIP : hostName ) + ":" + port;
  }
}
