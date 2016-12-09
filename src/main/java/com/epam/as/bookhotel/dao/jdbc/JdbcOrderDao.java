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
    void setIdFieldToPs(PreparedStatement ps, int id) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    String getInsertQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(INSERT_ORDER_PROPERTY_KEY));
        return propertyManager.getPropertyKey(INSERT_ORDER_PROPERTY_KEY);
    }

    @Override
    String getFindQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(FIND_ORDERS_PROPERTY_KEY));
        return propertyManager.getPropertyKey(FIND_ORDERS_PROPERTY_KEY);
    }

    @Override
    void setInsertQueryFieldToPs(PreparedStatement ps, Order entity) throws SQLException {

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
    void setFindQueryFieldToPs(PreparedStatement ps, Order entity) throws SQLException {

    }

    @Override
    Order setFindQueryRsToField(ResultSet rs, Order entity) throws SQLException {
        Order newEntity = new Order();
        newEntity.setId(rs.getInt(1));
        newEntity.setUserId(rs.getInt(2));
        newEntity.setFirstName(rs.getString(3));
        newEntity.setLastName(rs.getString(4));
        newEntity.setEmail(rs.getString(5));
        newEntity.setPhone(rs.getString(6));
        newEntity.setBed(rs.getInt(7));
        newEntity.setRoomType(rs.getString(8));
        Date checkInDate = rs.getDate(9);
        Date checkOutDate = rs.getDate(10);
        newEntity.setCheckIn(checkInDate.toLocalDate());
        newEntity.setCheckOut(checkOutDate.toLocalDate());
        newEntity.setStatus(rs.getString(11));
        return newEntity;
    }
}
