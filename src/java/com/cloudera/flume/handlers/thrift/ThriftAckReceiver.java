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

import com.cloudera.flume.handlers.endtoend.AckReceiver;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudera.flume.handlers.thrift.ThriftFlumeEventServer;

/**
 * Thrift implementation for AckReceiver.
 */
public class ThriftAckReceiver extends AckReceiver {
  static final Logger LOG = LoggerFactory.getLogger(ThriftAckReceiver.class);

  private final ThriftFlumeEventServer.Processor processor;
  private final TProtocol protocol;

  // interval before reconnection, in ms.
  private final static int reconnIntvl = 3000;

  public ThriftAckReceiver(TProtocol protocol, ThriftFlumeEventServer.Iface checkAck) {
    this.protocol = protocol;
    this.processor = new ThriftFlumeEventServer.Processor(checkAck);
  }

  @Override
  public void run() {
    while (true) {
      try {
        while (processor.process(protocol, protocol) == true) { }
      } catch (TException te) {
        LOG.debug(String.format("Connection is broken, " +
        "sleep %s seconds and connect again...", reconnIntvl/1000));
        try {
          Thread.sleep(reconnIntvl);
        } catch (Exception e) {
          LOG.info(e.getMessage());
          e.printStackTrace();
        }
      }
    }
  }
}
