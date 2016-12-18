package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class JdbcRoomDao extends JdbcDao<Room> implements RoomDao {

    JdbcRoomDao(Connection connection) {
        super(connection);
    }

    @Override
    void setFieldToPs(PreparedStatement ps, Room entity) throws SQLException {

    }


}
