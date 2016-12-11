package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.model.Room;

import java.util.List;

public class RoomService {

    public List<Room> findAllRooms(Room room) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {

        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao roomDao = daoFactory.getRoomDao();
            roomList = roomDao.findAll(room);
        }
        return roomList;
    }

    public List<Room> findAllRoomsByStatus(Room room) throws ConnectionPoolException, JdbcDaoException, PropertyManagerException {

        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao roomDao = daoFactory.getRoomDao();
            roomList = roomDao.findAllByParameter(room);
        }
        return roomList;
    }
}
