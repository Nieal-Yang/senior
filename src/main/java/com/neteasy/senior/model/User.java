package com.neteasy.senior.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "User")
@Table(name = "tb_user")
@Data
public class User implements Serializable {

    @Id
    public String id;

    @Column(name = "user_name")
    public String userName;
}
