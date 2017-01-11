package com.epam.bookhotel.dao.jdbc;

import com.epam.bookhotel.dao.OrderDao;
import com.epam.bookhotel.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.bookhotel.constant.Constants.*;

/**
 * Jdbc DAO for entity Order.
 */

class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcOrderDao.class);

    JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    Order setRsToField(ResultSet rs, Order order) throws SQLException {

        Order newOrder = new Order();
        newOrder.setId(rs.getInt(COLUMN_INDEX_1));
        newOrder.setUser(order.getUser());
        newOrder.setFirstName(rs.getString(COLUMN_INDEX_3));
        newOrder.setLastName(rs.getString(COLUMN_INDEX_4));
        newOrder.setEmail(rs.getString(COLUMN_INDEX_5));
        newOrder.setPhone(rs.getString(COLUMN_INDEX_6));
        newOrder.setBed(new Bed(rs.getInt(COLUMN_INDEX_7)));
        newOrder.setRoomType(new RoomType(rs.getString(COLUMN_INDEX_8)));
        newOrder.setCheckIn(rs.getDate(COLUMN_INDEX_9));
        newOrder.setCheckOut(rs.getDate(COLUMN_INDEX_10));
        newOrder.setStatus(new OrderStatus(rs.getString(COLUMN_INDEX_11)));
        newOrder.setFullCost(rs.getBigDecimal(COLUMN_INDEX_12));
        Room room = new Room();
        room.setNumber(rs.getInt(COLUMN_INDEX_13));
        room.setPrice(rs.getBigDecimal(COLUMN_INDEX_14));
        room.setPhoto(new Photo(rs.getInt(COLUMN_INDEX_15)));
        newOrder.setRoom(room);
        logger.debug("Found entity: {}", newOrder);
        return newOrder;
    }

}