package com.neteasy.senior.dao;

import com.neteasy.senior.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Order, String> {

}
