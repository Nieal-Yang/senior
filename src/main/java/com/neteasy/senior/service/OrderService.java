package com.neteasy.senior.service;

import com.neteasy.senior.annotation.NeedSetFieldValue;
import com.neteasy.senior.dao.OrderDao;
import com.neteasy.senior.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @NeedSetFieldValue
    public List<Order> findOrder() {
        return orderDao.findAll();
    }

}
