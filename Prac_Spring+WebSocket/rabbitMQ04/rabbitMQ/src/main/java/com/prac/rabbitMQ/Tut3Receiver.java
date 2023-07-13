package com.prac.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * acts as a message receiver in the pub-sub pattern using RabbitMQ
 *
 * receive1 & receive2: specify the queues from which the receiver should consume messages using @RabbitListener
 *
 */
public class Tut3Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")    // specify the queues from which the receiver should consume messages
    public void receive1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }

    /**
     * receive() is invoked by the receive1 and receive2 methods to perform the actual message processing
     * It takes the message content and the receiver id as parameters
     *
     * @param in
     * @param receiver
     * @throws InterruptedException
     */
    public void receive(String id, int receiver) throws InterruptedException {
        StopWatch watch = new StopWatch();  // `StopWatch` measure the time taken to process a message
        watch.start();                      // starts the stopwatch to measure the processing time
        System.out.println("instance " + receiver + " [x] Received '" + id + "'");  // prints a message when receiver has received a message
        doWork(id);                         // to simulate some work being done with the received message
        watch.stop();                       // stops the stopwatch after the message processing is completed
        System.out.println("instance " + receiver + " [x] Done in "
                + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String id) throws InterruptedException {
        for (char ch : id.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }

}
