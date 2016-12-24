package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.RoomTypeDao;
import com.epam.as.bookhotel.model.RoomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

class JdbcRoomTypeDao extends JdbcDao<RoomType> implements RoomTypeDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRoomTypeDao.class);
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;

    JdbcRoomTypeDao(Connection connection) {

        super(connection);
    }

    @Override
    RoomType setRsToField(ResultSet rs, RoomType entity) throws SQLException {

        RoomType foundRoomType = new RoomType();
        foundRoomType.setRoomType(rs.getString(INDEX_1));
        foundRoomType.setRoomTypeEn(rs.getString(INDEX_2));
        logger.debug("Found entity: \"{}\" {}", foundRoomType.getClass().getSimpleName(), foundRoomType);
        return foundRoomType;
    }
}
