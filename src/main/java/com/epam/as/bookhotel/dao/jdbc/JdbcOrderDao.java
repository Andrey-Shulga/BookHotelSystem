package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.*;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcOrderDao.class);
    private static final String INSERT_ORDER_PROPERTY_KEY = "insert.order";
    private static final String FIND_ORDERS_PROPERTY_KEY = "find.orders";
    private static final String FIND_ALL_ORDERS_PROPERTY_KEY = "find.all.orders";


    JdbcOrderDao(Connection connection) {
        super(connection);

    }

    @Override
    Order setFindALLQueryRsToField(ResultSet rs, Order entity) throws SQLException {
        Order newEntity = new Order();
        newEntity.setId(rs.getInt(1));
        User user = new User();
        user.setId(rs.getInt(2));
        user.setLogin(rs.getString(3));
        newEntity.setUser(user);
        newEntity.setFirstName(rs.getString(4));
        newEntity.setLastName(rs.getString(5));
        newEntity.setEmail(rs.getString(6));
        newEntity.setPhone(rs.getString(7));
        Bed bed = new Bed(rs.getInt(8));
        newEntity.setBed(bed);
        RoomType roomType = new RoomType(rs.getString(9));
        newEntity.setRoomType(roomType);
        Date checkInDate = rs.getDate(10);
        Date checkOutDate = rs.getDate(11);
        newEntity.setCheckIn(checkInDate.toLocalDate());
        newEntity.setCheckOut(checkOutDate.toLocalDate());
        OrderStatus status = new OrderStatus(rs.getString(12));
        newEntity.setStatus(status);
        return newEntity;
    }

    @Override
    String getFindAllQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(FIND_ALL_ORDERS_PROPERTY_KEY));
        return propertyManager.getPropertyKey(FIND_ALL_ORDERS_PROPERTY_KEY);
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

        ps.setInt(1, entity.getUser().getId());
        ps.setString(2, entity.getFirstName());
        ps.setString(3, entity.getLastName());
        ps.setString(4, entity.getEmail());
        ps.setString(5, entity.getPhone());
        ps.setInt(6, entity.getBed().getBed());
        ps.setString(7, entity.getRoomType().getRoomType());
        ps.setObject(8, entity.getCheckIn(), Types.DATE);
        ps.setObject(9, entity.getCheckOut(), Types.DATE);

    }

    @Override
    void setFindQueryFieldToPs(PreparedStatement ps, Order entity) throws SQLException {
        logger.debug("{} trying to FIND entities \"{}\" related by entity \"{}\" in database...",
                this.getClass().getSimpleName(), entity.getClass().getSimpleName(), entity.getUser(), entity.getUser().getId());
        ps.setInt(1, entity.getUser().getId());
    }

    @Override
    Order setFindQueryRsToField(ResultSet rs, Order entity) throws SQLException {
        Order newEntity = new Order();
        newEntity.setId(rs.getInt(1));
        newEntity.setUser(entity.getUser());
        newEntity.setFirstName(rs.getString(3));
        newEntity.setLastName(rs.getString(4));
        newEntity.setEmail(rs.getString(5));
        newEntity.setPhone(rs.getString(6));
        Bed bed = new Bed(rs.getInt(7));
        newEntity.setBed(bed);
        RoomType roomType = new RoomType(rs.getString(8));
        newEntity.setRoomType(roomType);
        Date checkInDate = rs.getDate(9);
        Date checkOutDate = rs.getDate(10);
        newEntity.setCheckIn(checkInDate.toLocalDate());
        newEntity.setCheckOut(checkOutDate.toLocalDate());
        OrderStatus status = new OrderStatus(rs.getString(11));
        newEntity.setStatus(status);
        return newEntity;
    }
}
