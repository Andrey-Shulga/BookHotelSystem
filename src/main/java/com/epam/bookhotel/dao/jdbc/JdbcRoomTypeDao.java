package com.epam.bookhotel.dao.jdbc;

import com.epam.bookhotel.dao.RoomTypeDao;
import com.epam.bookhotel.entity.RoomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Jdbc DAO for entity RoomType.
 */

class JdbcRoomTypeDao extends JdbcDao<RoomType> implements RoomTypeDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRoomTypeDao.class);

    JdbcRoomTypeDao(Connection connection) {

        super(connection);
    }

    @Override
    RoomType setRsToField(ResultSet rs, RoomType entity) throws SQLException {

        RoomType foundRoomType = new RoomType();
        foundRoomType.setRoomType(rs.getString(COLUMN_INDEX_1));
        foundRoomType.setRoomTypeEn(rs.getString(COLUMN_INDEX_2));
        logger.debug("Found entity: \"{}\" {}", foundRoomType.getClass().getSimpleName(), foundRoomType);
        return foundRoomType;
    }
}
