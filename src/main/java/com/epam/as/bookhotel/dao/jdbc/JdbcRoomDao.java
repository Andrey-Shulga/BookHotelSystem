package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.RoomStatus;
import com.epam.as.bookhotel.model.RoomType;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRoomDao extends JdbcDao<Room> implements RoomDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRoomDao.class);
    private static final String FIND_ALL_ROOMS_PROPERTY_KEY = "find.all.rooms";

    JdbcRoomDao(Connection connection) {
        super(connection);
    }

    @Override
    Room setFindALLQueryRsToField(ResultSet rs, Room entity) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt(1));
        RoomType roomType = new RoomType(rs.getString(2));
        room.setRoomType(roomType);
        Bed bed = new Bed(rs.getInt(3));
        room.setBed(bed);
        room.setNumber(rs.getInt(4));
        RoomStatus roomStatus = new RoomStatus(rs.getString(5));
        room.setRoomStatus(roomStatus);
        room.setPrice(rs.getBigDecimal(6));
        return room;
    }

    @Override
    String getFindAllQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(FIND_ALL_ROOMS_PROPERTY_KEY));
        return propertyManager.getPropertyKey(FIND_ALL_ROOMS_PROPERTY_KEY);
    }

    @Override
    String getInsertQuery() throws PropertyManagerException {
        return null;
    }

    @Override
    String getFindQuery() throws PropertyManagerException {
        return null;
    }

    @Override
    void setInsertQueryFieldToPs(PreparedStatement ps, Room entity) throws SQLException {

    }

    @Override
    void setFindQueryFieldToPs(PreparedStatement ps, Room entity) throws SQLException {

    }

    @Override
    Room setFindQueryRsToField(ResultSet rs, Room entity) throws SQLException {
        return null;
    }
}
