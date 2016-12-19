package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RoomService extends ParentService {

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);
    private static final String FIND_ALL_ROOMS_KEY = "find.all.rooms";
    private static final String FIND_ALL_FREE_ROOMS_BY_DATE_KEY = "find.free.rooms.on.date.range";

    private static final List<String> parameters = new ArrayList<>();

    public List<Room> findAllRooms(Room room) throws ServiceException {

        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao orderDao = daoFactory.getRoomDao();
            roomList = orderDao.findByParameters(room, parameters, FIND_ALL_ROOMS_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomList;
    }

    public List<Room> findAllFreeRoomsOnBookingDate(String checkIn, String checkOut) throws ServiceException {

        parameters.add(checkIn);
        parameters.add(checkOut);
        parameters.add(checkIn);
        parameters.add(checkOut);
        Room room = new Room();
        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao orderDao = daoFactory.getRoomDao();
            roomList = orderDao.findByParameters(room, parameters, FIND_ALL_FREE_ROOMS_BY_DATE_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomList;
    }

}
