package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.RoomType;
import com.epam.as.bookhotel.model.User;

import java.util.ArrayList;
import java.util.List;

public class RoomService extends ParentService {

    private static final String FIND_ALL_ROOMS_KEY = "find.all.rooms";
    private static final String FIND_ALL_FREE_ROOMS_BY_DATE_KEY = "find.free.rooms.on.date.range";
    private static final String USER_LOCALE_EN = "en";
    private static final String USER_LOCALE_RU = "ru";
    private static final List<String> parameters = new ArrayList<>();

    public List<Room> findAllRooms(Room room, User user) throws ServiceException {

        return getRoomsList(room, user, FIND_ALL_ROOMS_KEY);
    }


    public List<Room> findAllFreeRoomsOnBookingDate(String checkIn, String checkOut, User user) throws ServiceException {

        parameters.add(checkIn);
        parameters.add(checkOut);
        parameters.add(checkIn);
        parameters.add(checkOut);
        Room room = new Room();
        return getRoomsList(room, user, FIND_ALL_FREE_ROOMS_BY_DATE_KEY);
    }

    private List<Room> getRoomsList(Room room, User user, String key) throws ServiceException {
        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomDao orderDao = daoFactory.getRoomDao();
            roomList = orderDao.findByParameters(room, parameters, key);
            setParametersFromUserLocale(roomList, user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomList;
    }

    private void setParametersFromUserLocale(List<Room> roomList, User user) {

        for (Room r : roomList) {
            String userLocale = user.getLocale().getLocaleName();
            if (USER_LOCALE_EN.equals(userLocale)) {
                String roomTypeEn = r.getRoomType().getRoomTypeEn();
                r.setRoomType(new RoomType(roomTypeEn));
            }
            if (USER_LOCALE_RU.equals(userLocale)) {
                String roomTypeRu = r.getRoomType().getRoomTypeRu();
                r.setRoomType(new RoomType(roomTypeRu));
            }
        }
    }

}
