package com.epam.bookhotel.pool;

import com.epam.bookhotel.exception.ConnectionPoolException;
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

        ConnectionPool pool = null;
        boolean testResult = false;
        try {
            pool = new ConnectionPool();
            pool.fillPool();
            Connection connection = pool.getConnection();
            testResult = !connection.isClosed();
            pool.putConnectionToPool(connection);
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Exception while testing Connection pool", e);
        } finally {
            try {
                if (pool != null) pool.close();
            } catch (ConnectionPoolException e) {
                logger.error("Exception while testing Connection pool", e);
            }
        }
        assertEquals(isConnected, testResult);
    }

}