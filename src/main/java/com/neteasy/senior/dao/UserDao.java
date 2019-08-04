package com.neteasy.senior.dao;

import com.neteasy.senior.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao  extends JpaRepository<User, String> {

    @Query(value = "select t from User t where t.id = ?1")
    User myFindById(String id);

}
