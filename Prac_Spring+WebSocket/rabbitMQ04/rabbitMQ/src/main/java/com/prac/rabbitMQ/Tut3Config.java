package com.prac.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"tut3", "pub-sub", "publish-subscribe"})
@Configuration

public class Tut3Config {

    @Bean
    public FanoutExchange fanout() {
        // creates an instance of FanoutExchange with the name "tut.fanout"
        return new FanoutExchange("tut.fanout");
    }

    @Profile("receiver")  // following configuration class should be active only when the "receiver" profile is active
    private static class ReceiverConfig {

        /**
         * processed in a first-in-first-out (FIFO) fashion. messages that are received first are processed first, and sent first
         * public Queue: define beans of type Queue. They create instances of AnonymousQueue, which are temporary and auto-deleted queues.
         * @return
         */
        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        /**
         * A binding is a “connection” that you build between a queue and an exchange.
         * A binding is a relationship between an exchange and a queue
         * create bindings between :
         *               the fanout exchange (fanout)
         *               nd the auto-deleted queues (autoDeleteQueue1
         *               and autoDeleteQueue2)
         * @param fanout
         * @param autoDeleteQueue1
         * @return
         */
        @Bean
        public Binding binding1(FanoutExchange fanout,
                                Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
        }

        @Bean
        public Binding binding2(FanoutExchange fanout,
                                Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
        }

        /**
         * Tut3Receiver receiver: responsible for receiving messages in the `pub-sub` pattern
         * @return
         */
        @Bean
        public Tut3Receiver receiver() {
            return new Tut3Receiver();
        }
    }


    /**
     * Tut3Sender sender: responsible for sending messages in the pub-sub pattern
     * @return
     */
    @Profile("sender")    // the following bean definition should be active only when the "sender" profile is active
    @Bean
    public Tut3Sender sender() {
        return new Tut3Sender();
    }

}
