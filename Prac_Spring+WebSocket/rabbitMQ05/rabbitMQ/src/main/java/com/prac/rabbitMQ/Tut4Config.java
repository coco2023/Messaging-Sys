package com.prac.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * send messages to a direct exchange
 * supply the color as a routing key
 * receiving program will be able to select the color it wants to receive (or subscribe to).
 *
 */
@Profile({"tut4","routing"})
@Configuration

public class Tut4Config {

    /**
     * sending messages
     * @return
     */
    @Bean
    public DirectExchange direct() {
        return new DirectExchange("tut.direct");
    }

    /**
     * Receiving messages: create a new binding for each color
     * @return
     */
    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1a(DirectExchange direct,
                                 Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1)
                    .to(direct)
                    .with("orange");
        }


        @Bean
        public Binding binding1b(DirectExchange direct,
                                 Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1)
                    .to(direct)
                    .with("black");
        }

        @Bean
        public Binding binding2a(DirectExchange direct,
                                 Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2)
                    .to(direct)
                    .with("green");
        }

        @Bean
        public Binding binding2b(DirectExchange direct,
                                 Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2)
                    .to(direct)
                    .with("black");
        }

        @Bean
        public Tut4Receiver receiver() {
            return new Tut4Receiver();
        }

    }

    @Profile("sender")
    @Bean
    public Tut4Sender sender() {
        return new Tut4Sender();
    }

}
