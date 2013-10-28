package com.cloudera.flume.handlers.endtoend;

import com.cloudera.flume.core.EventAck;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * AckDistributor Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 24, 2013</pre>
 */
public class TestAckDistributor {
    private static final Logger LOG = LoggerFactory.getLogger(TestAckDistributor.class);

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addClient(ServiceClient client)
     */
    @Test
    public void testAddClient() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: run()
     */
    @Test
    public void testRun() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: addAck(EventAck ack)
     */
    @Test
    public void testAddAck() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: addAckAll(List<EventAck> ackList)
     * Test ConcurrentModificationException
     */
    @Test
    public void testAddAckAll() throws Exception {

        final AckDistributor distributor = new AckDistributor();

        Runnable producer = new Runnable() {
            @Override
            public void run() {
                List<EventAck> ackList = new ArrayList<EventAck>();
                for (int i = 0; i < 10000; i++) {
                    List hostlist = new ArrayList<String>();
                    hostlist.add(String.format("localhost:%d", i));
                    EventAck ack = new EventAck(String.format("%d-%d",
                            Thread.currentThread().getId(), i), hostlist);
                    ackList.add(ack);
                    LOG.info("Generate ack: " + ack.ackID);

                }
                distributor.addAckAll(ackList);
                ackList.clear();
            }
        };

        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        EventAck ack = distributor.takeAck();
                        LOG.info("Take ack: " + ack.ackID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        ExecutorService executor = new ThreadPoolExecutor(8, 160, 0,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(8));

        for (int i = 0; i < 10; i++) {
            for (int j=0; j< 5; j++) {
                executor.submit(producer);
            }
            executor.submit(consumer);
        }
        executor.shutdown();
        if (!executor.awaitTermination(600, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }

    /**
     * Method: getHostPortString(String hostName, String hostIP, int port)
     */
    @Test
    public void testGetHostPortString() throws Exception {
        //TODO: Test goes here...
    }


} 
