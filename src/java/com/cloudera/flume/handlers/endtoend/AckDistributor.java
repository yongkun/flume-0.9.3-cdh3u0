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
import java.util.concurrent.TimeUnit;

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
        while(true) {
            try {
                EventAck ack = this.takeAck();
                if ( ack == null ) continue;

                List<String> host = ack.hostList;
                String ackId = ack.ackID;

                int hostListLength = host.size();
                if ( hostListLength < 1 ) {
                    LOG.info("No host route for " + ackId);
                    continue;
                }

                String hostAndPort = host.get(hostListLength-1);
                ServiceClient sClient = clients.get(hostAndPort);
                if ( sClient == null ) {
                    LOG.error("No connection to " + hostAndPort + " for " + ackId +
                            ". Please check the host name.");
                    //TODO put this ack to a re-try queue
                    //TODO broadcast this ack with all active connections to that host
                    continue;
                }

                ack.hostList.remove(hostListLength - 1);

                sendAck(sClient, ack);

            } catch (Exception e) {
                LOG.info(e.getMessage());
            }
        } //end while true
    }

    public void sendAck(ServiceClient client, EventAck ack) {
        // implement it by different RPC: thrift or avro.
        LOG.error("sendAck() is not implemented.");
    }

    public void addAck(EventAck ack) {
        try {
            //synchronized (ackQueue) {
                // this can block the collector. maybe offer() is better.
                // but offer() may lose Acks.
                ackQueue.put(ack);
            //}
            LOG.debug("Add ack to queue: ack ID " + (ack == null ? "" : ack.ackID));
        } catch (InterruptedException ie) {
            LOG.error("interrupted while waiting");
        } catch (ClassCastException cce) {
            LOG.error("Ack element cast error.");
        } catch (NullPointerException npe) {
            LOG.error("Ack is null.");
        } catch (IllegalArgumentException iae) {
            LOG.error("Ack type is wrong.");
        } catch (Exception e) {
            LOG.warn(e.getMessage());
        }
    }

    public void addAckAll(List<EventAck> ackList) {
        for (EventAck ack : ackList) {
            this.addAck(ack);
        }
    }

    public EventAck takeAck() {
        EventAck ack = null;
        try {
            // check queue size before take().
            // when using synchronize here, it will be blocked here
            // when queue is empty, other threads cannot put elements to queue.
            //if (ackQueue.size() > 0) {
            //    synchronized (ackQueue) {
                    ack = ackQueue.take();
            //    }
            //}
            /*if (ack == null) {
                Thread.sleep(1000);
            }*/
            if (ack != null) {
                LOG.debug("Take ack from queue: ack ID " + ack.ackID);
            }
        } catch (InterruptedException ie) {
            LOG.warn("Interrupted while taking ack from queue.");
        } catch (Exception e) {
            LOG.warn(e.getMessage());
        }
        return ack;
    }

    public static String getHostPortString(String hostName, String hostIP, int port) {
        boolean useIP = FlumeConfiguration.get().getBoolean("ack.hostlist.using.ip", true);
        return (useIP ? hostIP : hostName) + ":" + port;
    }
}
