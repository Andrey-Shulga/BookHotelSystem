package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcOrderDao.class);
    private static final String INSERT_ORDER_PROPERTY_KEY = "insert.order";
    private static final String FIND_ORDERS_PROPERTY_KEY = "find.orders";
    private Connection connection;

    JdbcOrderDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    String getInsertQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(INSERT_ORDER_PROPERTY_KEY));
        return propertyManager.getPropertyKey(INSERT_ORDER_PROPERTY_KEY);
    }

    @Override
    String getUpdateQuery() throws PropertyManagerException {
        return null;
    }

    @Override
    String getFindQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(FIND_ORDERS_PROPERTY_KEY));
        return propertyManager.getPropertyKey(FIND_ORDERS_PROPERTY_KEY);
    }

    @Override
    void setInsertFieldToPs(PreparedStatement ps, Order entity) throws SQLException {

        ps.setInt(1, entity.getUserId());
        ps.setString(2, entity.getFirstName());
        ps.setString(3, entity.getLastName());
        ps.setString(4, entity.getEmail());
        ps.setString(5, entity.getPhone());
        ps.setInt(6, entity.getBed());
        ps.setString(7, entity.getRoomType());
        ps.setObject(8, entity.getCheckIn(), Types.DATE);
        ps.setObject(9, entity.getCheckOut(), Types.DATE);

    }

    @Override
    void setFindFieldToPs(PreparedStatement ps, Order entity) throws SQLException {

    }

    @Override
    void setUpdateFieldToPs(PreparedStatement ps, Order entity) throws SQLException {

    }

    @Override
    void setRsToField(ResultSet ps, Order entity) throws SQLException {

    }
}
