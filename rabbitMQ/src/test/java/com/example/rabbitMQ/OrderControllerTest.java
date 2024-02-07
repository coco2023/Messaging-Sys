package com.example.rabbitMQ;

import com.example.rabbitMQ.rabbitMQ.RabbitMQSender;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RabbitMQSender rabbitMQSender;

    @Test
    public void testSendOrder() throws Exception {
        String orderId = "12345";
        long ttl = 10000; // 10 seconds

        mockMvc.perform(post("/api/orders/send")
                        .param("orderId", orderId)
                        .param("ttl", String.valueOf(ttl)))
                .andExpect(status().isOk())
                .andExpect(content().string("Order sent"));

        verify(rabbitMQSender, times(1)).sendOrder(orderId, ttl);
    }

}
