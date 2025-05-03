package com.example.order_service.controller;

import com.example.base_domains.dto.Order;
import com.example.base_domains.dto.OrderEvent;
import com.example.order_service.kafka.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

//    public OrderController(OrderProducer orderProducer) {
//        this.orderProducer = orderProducer;
//    }

    @PostMapping("/order")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order Status is PENDING");
        orderEvent.setOrder(order);
        orderProducer.sendMessage(orderEvent);
        // Here you can add logic to save the order to a database or perform other operations
        return "Order placed successfully";
    }
}
