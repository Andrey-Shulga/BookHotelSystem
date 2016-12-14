package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomService extends ParentService {

    private static final String FIND_ALL_ROOMS_KEY = "find.all.rooms";
    private static final String FIND_ALL_ROOMS_BY_STATUS_KEY = "find.all.rooms.by.status";
    private static final String ROOM_FREE_STATUS = "free";
    private List<String> parameters = new ArrayList<>();

    public List<Room> findAllRooms(Room room) throws ServiceException {

        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao roomDao = daoFactory.getRoomDao();
            roomList = roomDao.findByParameters(room, parameters, FIND_ALL_ROOMS_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomList;
    }

    public List<Room> findAllRoomsByStatus(Room room) throws ServiceException {

        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao roomDao = daoFactory.getRoomDao();
            parameters.add(ROOM_FREE_STATUS);
            roomList = roomDao.findByParameters(room, parameters, FIND_ALL_ROOMS_BY_STATUS_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomList;
    }
}
