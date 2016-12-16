package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class JdbcRoomDao extends JdbcDao<Room> implements RoomDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRoomDao.class);

    JdbcRoomDao(Connection connection) {
        super(connection);
    }

    @Override
    void setFieldToPs(PreparedStatement ps, Room entity) throws SQLException {

    }


}
