package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.RoomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomService extends ParentService {

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);
    private static final String FIND_ALL_ROOMS_KEY = "find.all.rooms";
    private static final String FIND_ALL_FREE_ROOMS_BY_DATE_KEY = "find.free.rooms.on.date.range";
    private List<String> parameters = new ArrayList<>();

    public List<Room> findAllRooms(Room room) throws ServiceException {

        List<List<Object>> resultList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao roomDao = daoFactory.getRoomDao();
            resultList = roomDao.findByParameters(room, parameters, FIND_ALL_ROOMS_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        List<Room> roomList = new ArrayList<>();
        setRowsToRoom(resultList, roomList);
        return roomList;
    }

    public List<Room> findAllFreeRoomsOnBookingDate(String checkIn, String checkOut) throws ServiceException {
        Room room = new Room();
        List<List<Object>> resultList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao roomDao = daoFactory.getRoomDao();
            parameters.add(checkIn);
            parameters.add(checkOut);
            parameters.add(checkIn);
            parameters.add(checkOut);
            resultList = roomDao.findByParameters(room, parameters, FIND_ALL_FREE_ROOMS_BY_DATE_KEY);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        List<Room> roomList = new ArrayList<>();
        setRowsToRoom(resultList, roomList);
        return roomList;
    }

    private void setRowsToRoom(List<List<Object>> resultList, List<Room> roomList) {
        for (List<Object> rows : resultList) {
            Room foundRoom = new Room();
            foundRoom.setId((Integer) rows.get(0));
            foundRoom.setRoomType(new RoomType((String) rows.get(1)));
            foundRoom.setBed(new Bed((Integer) rows.get(2)));
            foundRoom.setNumber((Integer) rows.get(3));
            foundRoom.setPrice((BigDecimal) rows.get(4));
            roomList.add(foundRoom);
            logger.debug("Found entity: {}", foundRoom);
        }
    }
}
