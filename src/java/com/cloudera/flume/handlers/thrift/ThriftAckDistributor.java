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

import java.util.List;

import com.cloudera.flume.core.EventAck;
import com.cloudera.flume.handlers.endtoend.AckDistributor;

import com.cloudera.flume.handlers.endtoend.ServiceClient;
import com.cloudera.flume.handlers.thrift.ThriftFlumeEventServer.Client;
import com.cloudera.flume.handlers.thrift.ThriftEventAckAdaptor;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thrift implementation of the AckDistributor.
 */
public class ThriftAckDistributor extends AckDistributor {
  private static final Logger LOG = LoggerFactory.getLogger(ThriftAckDistributor.class);

  public ThriftAckDistributor() {
    super();
  }
  
  @Override
  public void sendAck(ServiceClient sClient, EventAck ack) {
      try {
          Client client = (Client)(sClient.getClient());
          client.checkAck(ThriftEventAckAdaptor.convert(ack));
          LOG.info(String.format("Send ack: %s", ack.ackID));
      } catch (TException te) {
          LOG.error(te.getMessage());
      } catch (Exception e) {
          LOG.error(e.getMessage());
      }
  }

}
