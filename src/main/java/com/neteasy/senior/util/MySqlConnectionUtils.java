package com.neteasy.senior.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlConnectionUtils.class);

    public static Connection getConnection() {
        Connection con = null;
        try {
            //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                    "root", "19910218yx."); //链接本地MYSQL
            LOG.debug("get connection success");
        } catch (Exception e) {
            LOG.error("", e);
        }
        return con;

    }
}
