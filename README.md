Flume MA
========

This is a repository for the flume source code we are using inside our company.
It is forked from Cloudera's 0.9.3-cdh3u0 distribution (Flume-OG).

Two major enhancements:

1. Masterless ACK (MA)
----------------------

This enhancement aims to increase the reliability and throughput of the whole distributed collecting system.

Flume provides End-to-End delivery mode to guarantee the data delivery; an acknowledgement message (ACK) is sent back to original node to confirm the successful delivery of a group of messages. 

However, the ACKs are sent back through master, which could be a single-point-of-failure or bottle neck of the whole system.
Therefore, I re-designed the ACKs system to let the ACK go back via the route of Event.

Main enhancements

..1. Distribute ACK traffic to each flume node, ACK goes back to original agent throught the route of event.

..2. Reuse the connection for event transmission.

..3. Buffer input/output stream for thrift to improve throughput. https://github.com/yongkun/thrift

Apache JIRA is here, with some documents https://issues.apache.org/jira/browse/FLUME-640
This enhancement was supposed to be merged into 0.10 if Flume was not upgraded to NG.

2. Append to HDFS with new file rotation method
-----------------------------------------------

Use HDFS append() and change the file rotation mechanism to create large HDFS files, which could increase the performance of Map/Reduce program when using these files as input, and reduce the number of block mapping entries in Hadoop NameNode.



##### This modified version has been heavily used inside our company (Rakuten, http://en.wikipedia.org/wiki/Rakuten), with single collector receiving more than 300GB data per day (more than 328 million events per day, peak throughput is about 26K event per second).

Contact
yongkun at gmail.com

Repository
https://github.com/yongkun/flume-0.9.3-cdh3u0-rakuten
