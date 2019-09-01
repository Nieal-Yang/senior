package com.neteasy.senior.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "User")
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    public String id;

    @Column(name = "user_name")
    public String userName;
}
