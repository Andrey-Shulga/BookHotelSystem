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
    private static final String FIND_ALL_ROOMS_QUERY = "find.all.rooms";
    private static final String FIND_ROOMS_BY_STATUS_QUERY = "find.all.rooms.by.status";

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
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(FIND_ALL_ROOMS_QUERY));
        return propertyManager.getPropertyKey(FIND_ALL_ROOMS_QUERY);
    }

    @Override
    String getInsertQuery() throws PropertyManagerException {
        return null;
    }

    @Override
    String getFindQuery() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(QUERY_PROPERTY_FILE);
        logger.debug("Using prepare statement command: {}", propertyManager.getPropertyKey(FIND_ROOMS_BY_STATUS_QUERY));
        return propertyManager.getPropertyKey(FIND_ROOMS_BY_STATUS_QUERY);
    }

    @Override
    void setInsertQueryFieldToPs(PreparedStatement ps, Room entity) throws SQLException {

    }

    @Override
    void setFindQueryFieldToPs(PreparedStatement ps, Room entity) throws SQLException {
        ps.setString(1, entity.getRoomStatus().getRoomStatus());
    }

    @Override
    Room setFindQueryRsToField(ResultSet rs, Room entity) throws SQLException {
        Room newRoom = new Room();
        newRoom.setId(rs.getInt(1));
        newRoom.setRoomType(new RoomType(rs.getString(2)));
        newRoom.setBed(new Bed(rs.getInt(3)));
        newRoom.setNumber(rs.getInt(4));
        newRoom.setRoomStatus(new RoomStatus(rs.getString(5)));
        newRoom.setPrice(rs.getBigDecimal(6));
        return newRoom;
    }
}
