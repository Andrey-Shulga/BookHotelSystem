package com.epam.as.bookhotel.pool;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

public class ConnectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static final boolean isConnected = true;


    @Test
    public void hasConnectionWithDatabase() {

        Connection connection;
        boolean testResult = false;
        try {
            ConnectionPool pool = new ConnectionPool();
            pool.fillPool();
            connection = pool.getConnection();
            testResult = !connection.isClosed();
            pool.close();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Exception while testing Connection pool", e);
        }
        assertEquals(isConnected, testResult);
    }

}