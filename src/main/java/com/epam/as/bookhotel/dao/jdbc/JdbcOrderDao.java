package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcOrderDao.class);
    private static final int INDEX_1 = 1;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;
    private static final int INDEX_5 = 5;
    private static final int INDEX_6 = 6;
    private static final int INDEX_7 = 7;
    private static final int INDEX_8 = 8;
    private static final int INDEX_9 = 9;
    private static final int INDEX_10 = 10;
    private static final int INDEX_11 = 11;
    private static final int INDEX_12 = 12;
    private static final int INDEX_13 = 13;
    private static final int INDEX_14 = 14;
    private static final int INDEX_15 = 15;


    JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    Order setRsToField(ResultSet rs, Order order) throws SQLException {

        Order newOrder = new Order();
        newOrder.setId(rs.getInt(INDEX_1));
        newOrder.setFirstName(rs.getString(INDEX_3));
        newOrder.setLastName(rs.getString(INDEX_4));
        newOrder.setEmail(rs.getString(INDEX_5));
        newOrder.setPhone(rs.getString(INDEX_6));
        newOrder.setBed(new Bed(rs.getInt(INDEX_7)));
        RoomType roomType = new RoomType();
        roomType.setRoomTypeEn(rs.getString(INDEX_8));
        roomType.setRoomTypeRu(rs.getString(INDEX_9));
        newOrder.setRoomType(roomType);
        newOrder.setCheckIn(rs.getDate(INDEX_10));
        newOrder.setCheckOut(rs.getDate(INDEX_11));
        newOrder.setStatus(new OrderStatus(rs.getString(INDEX_12)));
        newOrder.setFullCost(rs.getBigDecimal(INDEX_13));
        Room room = new Room();
        room.setNumber(rs.getInt(INDEX_14));
        room.setPrice(rs.getBigDecimal(INDEX_15));
        newOrder.setRoom(room);
        logger.debug("Found entity: {}", newOrder);
        return newOrder;
    }




}
