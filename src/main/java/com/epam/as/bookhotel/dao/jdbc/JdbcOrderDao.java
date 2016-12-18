package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {

    JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    void setFieldToPs(PreparedStatement ps, Order entity) throws SQLException {

        ps.setInt(1, entity.getUser().getId());
        ps.setString(2, entity.getFirstName());
        ps.setString(3, entity.getLastName());
        ps.setString(4, entity.getEmail());
        ps.setString(5, entity.getPhone());
        ps.setInt(6, entity.getBed().getBed());
        ps.setString(7, entity.getRoomType().getRoomType());
        ps.setDate(8, entity.getCheckIn());
        ps.setDate(9, entity.getCheckOut());

    }


}
