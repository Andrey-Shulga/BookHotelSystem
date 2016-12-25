package com.epam.as.bookhotel.dao.jdbc;

import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.NonUniqueFieldException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.RoomType;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;

class JdbcRoomDao extends JdbcDao<Room> implements RoomDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRoomDao.class);
    private static final String NON_UNIQUE_FIELD_ERROR_CODE = "23505";
    private static final String QUERY_PROPERTY_FILE = getQueryPropertyFile();
    private static final String ADD_ROOM_WITH_PHOTO = "add.room.no.photo";
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;
    private static final int INDEX_5 = 5;
    private static final int SCALE_ROUND = 2;
    private Connection connection;

    JdbcRoomDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    Room setRsToField(ResultSet rs, Room room) throws SQLException {

        Room newRoom = new Room();
        newRoom.setId(rs.getInt(INDEX_1));
        newRoom.setRoomType(new RoomType(rs.getString(INDEX_2)));
        newRoom.setBed(new Bed(rs.getInt(INDEX_3)));
        newRoom.setNumber(rs.getInt(INDEX_4));
        newRoom.setPrice(rs.getBigDecimal(INDEX_5).setScale(SCALE_ROUND, BigDecimal.ROUND_HALF_DOWN));
        logger.debug("Found entity: {}", newRoom);
        return newRoom;
    }

    @Override
    public void addRoomWithPhoto(Room room) throws JdbcDaoException {

        try {
            PropertyManager pm = new PropertyManager(QUERY_PROPERTY_FILE);
            String query = pm.getPropertyKey(ADD_ROOM_WITH_PHOTO);
            if (room.getId() == null) {
                if (connection == null) logger.debug("conn null");
                logger.debug("{} trying to INSERT entity \"{}\" to database...", this.getClass().getSimpleName(), room);
                try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(INDEX_1, room.getNumber());
                    ps.setInt(INDEX_2, room.getBed().getBed());
                    ps.setString(INDEX_3, room.getRoomType().getRoomType());
                    ps.setBigDecimal(INDEX_4, room.getPrice());
                    InputStream in = room.getPhotoPart().getInputStream();
                    ps.setBlob(INDEX_5, in);
                    ps.execute();
                    setId(room, ps);
                } catch (SQLException e) {
                    if (NON_UNIQUE_FIELD_ERROR_CODE.equals(e.getSQLState()))
                        throw new NonUniqueFieldException(e);
                    throw new JdbcDaoException(e);
                } catch (IOException e) {
                    throw new JdbcDaoException(e);
                }
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(e);
        }


    }
}