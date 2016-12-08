package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.OrderDao;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {
    private Connection connection;

    JdbcOrderDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    String getInsertQuery() throws PropertyManagerException {
        return null;
    }

    @Override
    String getUpdateQuery() throws PropertyManagerException {
        return null;
    }

    @Override
    String getFindQuery() throws PropertyManagerException {
        return null;
    }

    @Override
    void setInsertFieldToPs(PreparedStatement ps, Order entity) throws SQLException {

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
