package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomService extends ParentService {

    private static final String FIND_ALL_ROOMS_KEY = "find.all.rooms";
    private static final String FIND_ALL_ROOMS_BY_STATUS_KEY = "find.all.rooms.by.status";
    private static final String ROOM_FREE_STATUS = "free";
    private List<String> parameters = new ArrayList<>();

    public List<Room> findAllRooms(Room room) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {

        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao roomDao = daoFactory.getRoomDao();
            roomList = roomDao.findByParameters(room, parameters, FIND_ALL_ROOMS_KEY);
        }
        return roomList;
    }

    public List<Room> findAllRoomsByStatus(Room room) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {

        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao roomDao = daoFactory.getRoomDao();
            parameters.add(ROOM_FREE_STATUS);
            roomList = roomDao.findByParameters(room, parameters, FIND_ALL_ROOMS_BY_STATUS_KEY);
        }
        return roomList;
    }
}
