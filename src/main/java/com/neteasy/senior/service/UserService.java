package com.neteasy.senior.service;

import com.neteasy.senior.annotation.MyCached;
import com.neteasy.senior.dao.UserDao;
import com.neteasy.senior.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    public List<User> findUser() {
        List<User> users = userDao.findAll();
        return users;

    }


    @MyCached(key = "#userId")
    public User findUserById(String userId){
        return userDao.myFindById(userId);
    }

}
