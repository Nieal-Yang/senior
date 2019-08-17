package com.neteasy.senior.pool;

import com.neteasy.senior.util.MySqlConnectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class MyConnectPool implements ConnectionPool {

    private static final Logger LOG = LoggerFactory.getLogger(MyConnectPool.class);


    private int initSize;
    private int maxSize;
    private int idleCount;
    private long waitTime;

    private BlockingDeque<Connection> idle; //可外借的连接
    private BlockingDeque<Connection> busy; //可外借的连接

    private AtomicInteger activeSize = new AtomicInteger(0);


    @Override
    public void init(int initSize, int maxSize, int idleCount, long waitTime) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.idleCount = idleCount;
        this.waitTime = waitTime;

        idle = new LinkedBlockingDeque<>();
        busy = new LinkedBlockingDeque<>();

        initConnection(initSize);
    }

    private void initConnection(int initSize) {
        for (int i = 0; i < initSize; i++) {
            if (activeSize.get() < maxSize) {
                if (activeSize.incrementAndGet() < initSize) {
                    Connection connection = MySqlConnectionUtils.getConnection();
                    idle.offer(connection);
                } else {
                    activeSize.decrementAndGet();
                }
            }
        }
    }

    @Override
    public void destroy() {
        idle = null;
        maxSize = 0;
    }

    @Override
    public Connection get() throws InterruptedException, TimeoutException {
        Connection connection = idle.poll();
        if (connection != null) {
            busy.offer(connection);
            return connection;
        }

        if (activeSize.get() < maxSize) {
            if (activeSize.incrementAndGet() < initSize) {
                connection = MySqlConnectionUtils.getConnection();
                busy.offer(connection);
                return connection;
            } else {
                activeSize.decrementAndGet();
            }
        }

        connection = idle.poll(waitTime, TimeUnit.MILLISECONDS);
        if (connection == null) {
            throw new TimeoutException("get connection timeout");
        }

        return connection;
    }

    @Override
    public void recycle(Connection conn) {
        if (conn != null) {
            boolean res = busy.remove(conn);
            try {
                if (res) {
                    if (idleCount < idle.size()) {
                        conn.close();
                        activeSize.decrementAndGet();
                        return;
                    }
                    boolean res1 = idle.offer(conn);
                    if (!res1) {
                        conn.close();
                        activeSize.decrementAndGet();
                    }
                } else {
                    conn.close();
                    activeSize.decrementAndGet();
                }
            } catch (SQLException e) {
                LOG.error("", e);
            }
        }

    }
}
