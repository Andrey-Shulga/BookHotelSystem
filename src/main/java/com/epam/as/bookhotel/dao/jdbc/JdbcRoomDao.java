package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.RoomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

class JdbcRoomDao extends JdbcDao<Room> implements RoomDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRoomDao.class);
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;
    private static final int INDEX_5 = 5;

    JdbcRoomDao(Connection connection) {
        super(connection);
    }

    @Override
    Room setRsToField(ResultSet rs, Room room) throws SQLException {
        Room newRoom = new Room();
        newRoom.setId(rs.getInt(INDEX_1));
        newRoom.setRoomType(new RoomType(rs.getString(INDEX_2)));
        newRoom.setBed(new Bed(rs.getInt(INDEX_3)));
        newRoom.setNumber(rs.getInt(INDEX_4));
        newRoom.setPrice(rs.getBigDecimal(INDEX_5));
        logger.debug("Found entity: {}", newRoom);
        return newRoom;
    }

}
