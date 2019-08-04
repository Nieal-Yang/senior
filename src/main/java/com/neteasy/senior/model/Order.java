package com.neteasy.senior.model;

import com.neteasy.senior.annotation.NeedSetValue;
import com.neteasy.senior.dao.UserDao;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_order")
@Data
public class Order implements Serializable {

    @Id
    public String id;

    @Column(name = "customer_id")
    public String customerId;

    @NeedSetValue(beanClass = UserDao.class, param = "customerId", method = "myFindById", targetField = "userName")
    @Column(name = "customer_name")
    public String customerName;

}
