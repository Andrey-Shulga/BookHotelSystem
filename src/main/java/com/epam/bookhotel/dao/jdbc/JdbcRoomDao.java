package com.epam.bookhotel.dao.jdbc;

import com.epam.bookhotel.dao.RoomDao;
import com.epam.bookhotel.entity.Bed;
import com.epam.bookhotel.entity.Photo;
import com.epam.bookhotel.entity.Room;
import com.epam.bookhotel.entity.RoomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.bookhotel.constant.ConstantsHolder.*;

/**
 * Jdbc DAO for entity Room.
 */

class JdbcRoomDao extends JdbcDao<Room> implements RoomDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRoomDao.class);
    private static final int SCALE_ROUND = 2;

    JdbcRoomDao(Connection connection) {
        super(connection);
    }

    @Override
    Room setRsToField(ResultSet rs, Room room) throws SQLException {

        Room newRoom = new Room();
        newRoom.setId(rs.getInt(COLUMN_INDEX_1));
        newRoom.setRoomType(new RoomType(rs.getString(COLUMN_INDEX_2)));
        newRoom.setBed(new Bed(rs.getInt(COLUMN_INDEX_3)));
        newRoom.setNumber(rs.getInt(COLUMN_INDEX_4));
        newRoom.setPrice(rs.getBigDecimal(COLUMN_INDEX_5).setScale(SCALE_ROUND, BigDecimal.ROUND_HALF_DOWN));
        newRoom.setPhoto(new Photo(rs.getInt(COLUMN_INDEX_6)));
        logger.debug("Found entity: {}", newRoom);
        return newRoom;
    }

}