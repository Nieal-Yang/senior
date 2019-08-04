package com.neteasy.senior.controller;

import com.neteasy.senior.model.Order;
import com.neteasy.senior.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @ResponseBody
    private List<Order> getOrderInfo(){
        return orderService.findOrder();
    }

}
