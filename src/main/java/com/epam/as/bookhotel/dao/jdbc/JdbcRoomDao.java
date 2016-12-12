package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.RoomStatus;
import com.epam.as.bookhotel.model.RoomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class JdbcRoomDao extends JdbcDao<Room> implements RoomDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRoomDao.class);

    JdbcRoomDao(Connection connection) {
        super(connection);
    }

    @Override
    void setFieldToPs(PreparedStatement ps, Room entity) throws SQLException {

    }


    @Override
    Room setRsToField(ResultSet rs, Room entity) throws SQLException {
        Room newRoom = new Room();
        newRoom.setId(rs.getInt(1));
        newRoom.setRoomType(new RoomType(rs.getString(2)));
        newRoom.setBed(new Bed(rs.getInt(3)));
        newRoom.setNumber(rs.getInt(4));
        newRoom.setRoomStatus(new RoomStatus(rs.getString(5)));
        newRoom.setPrice(rs.getBigDecimal(6));
        logger.debug("Found entity: {}", newRoom);
        return newRoom;
    }
}
