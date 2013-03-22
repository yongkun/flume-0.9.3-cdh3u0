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
package com.cloudera.flume.core;

import java.util.List;
import java.util.ArrayList;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ack for the Event. It contains the ack ID and the hosts list that the
 * Event has passed by. When Event arrives at the destination, being sunk to 
 * the hdfs and the sink file is closed, the host list will be passed to the
 * ack. The reverted list is the route of the ack. The ack will go to the 
 * original host (the first element of the host list) by this route.
 */
public class EventAck {
  static final Logger LOG = LoggerFactory.getLogger(EventAck.class);

  public String ackID;

  // List of hosts:port the Event has passed by
  // Reverted list is the route for ack
  // Each item is a String of "HostName:Port" 
  public List<String> hostList;

  public EventAck() {
  }

  public EventAck(String ackID, List<String> hostList) {
    this.ackID = ackID;
    this.hostList = hostList;
  }

  public boolean isDestination() {
    if ( this.hostList == null || this.hostList.size() == 0 ) return true;
    return false;
  }

  public List<String> getHostList() {
    return this.hostList;
  }

  public void copy(EventAck ea) {
    if ( this.hostList == null )
      this.hostList = new ArrayList<String>();

    this.ackID = ea.ackID;
    if ( ea.hostList != null ) {
      for (int i=0; i<ea.hostList.size(); i++)
        this.hostList.add(ea.hostList.get(i));
    }
  }
}
