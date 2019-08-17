package com.neteasy.senior.pool;

import java.sql.Connection;
import java.util.concurrent.TimeoutException;

public interface ConnectionPool {

    /**
     * 初始化
     * @param initSize
     * @param maxSize
     * @param idleCount
     * @param waitTime
     */
    void init(int initSize, int maxSize, int idleCount, long waitTime);

    /**
     * 销毁
     */
    void destroy();

    /**
     *
     * @return Connection
     * @throws InterruptedException
     */
    Connection get() throws InterruptedException, TimeoutException;

    /**
     *
     * @param conn
     */
    void recycle(Connection conn);

}
