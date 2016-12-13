package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.model.OrderStatus;
import com.epam.as.bookhotel.model.RoomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcOrderDao.class);
    private static final String INSERT_ORDER_QUERY = "insert.order";
    private static final String FIND_ORDERS_BY_ID_QUERY = "find.orders";
    private static final String FIND_ALL_ORDERS_QUERY = "find.all.orders";


    JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    void setUpdateFieldToPs(PreparedStatement ps, Order entity) throws SQLException {
        ps.setString(1, String.valueOf(entity.getId()));
    }


    @Override
    void setFindFieldToPs(PreparedStatement ps, Order entity) throws SQLException {

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
    Order setRsToField(ResultSet rs, Order entity) throws SQLException {
        Order newEntity = new Order();
        newEntity.setId(rs.getInt(1));
        newEntity.setUser(entity.getUser());
        newEntity.setFirstName(rs.getString(3));
        newEntity.setLastName(rs.getString(4));
        newEntity.setEmail(rs.getString(5));
        newEntity.setPhone(rs.getString(6));
        newEntity.setBed(new Bed(rs.getInt(7)));
        newEntity.setRoomType(new RoomType(rs.getString(8)));
        Date checkInDate = rs.getDate(9);
        Date checkOutDate = rs.getDate(10);
        newEntity.setCheckIn(checkInDate.toLocalDate());
        newEntity.setCheckOut(checkOutDate.toLocalDate());
        newEntity.setStatus(new OrderStatus(rs.getString(11)));
        logger.debug("Found entity: {}", newEntity);
        return newEntity;
    }
}
