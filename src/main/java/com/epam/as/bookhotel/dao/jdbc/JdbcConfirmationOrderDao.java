package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.ConfirmationOrderDao;
import com.epam.as.bookhotel.model.ConfirmationOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcConfirmationOrderDao extends JdbcDao<ConfirmationOrder> implements ConfirmationOrderDao {

    JdbcConfirmationOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    void setUpdateFieldToPs(PreparedStatement ps, ConfirmationOrder entity) throws SQLException {

    }

    @Override
    void setFindFieldToPs(PreparedStatement ps, ConfirmationOrder entity) throws SQLException {
        ps.setInt(1, entity.getOrder().getId());
        ps.setInt(2, entity.getRoom().getId());

    }

    @Override
    ConfirmationOrder setRsToField(ResultSet rs, ConfirmationOrder entity) throws SQLException {
        return null;
    }
}
