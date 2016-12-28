package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.PhotoDao;
import com.epam.as.bookhotel.dao.RoomDao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.NonUniqueFieldException;
import com.epam.as.bookhotel.exception.RoomExistException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.User;

import java.util.ArrayList;
import java.util.List;

public class RoomService extends ParentService {

    private static final String FIND_ALL_ROOMS_KEY = "find.all.rooms";
    private static final String FIND_ALL_FREE_ROOMS_BY_DATE_KEY = "find.free.rooms.on.date.range";
    private static final String INSERT_ROOM_NO_PHOTO_KEY = "insert.room.no.photo";
    private static final String INSERT_ROOM_WITH_PHOTO_KEY = "insert.room.with.photo";
    private static final String INSERT_PHOTO_KEY = "insert.photo";
    private final List<String> parameters = new ArrayList<>();

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
            RoomDao roomDao = daoFactory.getRoomDao();
            roomList = roomDao.findByParameters(room, parameters, key, user.getLocale().getLocaleName());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomList;
    }

    public Room addRoom(Room room) throws ServiceException {

        try (DaoFactory daoFactory = DaoFactory.createFactory()) {

            RoomDao roomDao = daoFactory.getRoomDao();
            if (room.getPhoto() == null) {

                parameters.add(String.valueOf(room.getNumber()));
                parameters.add(String.valueOf(room.getBed().getBed()));
                parameters.add(room.getRoomType().getRoomType());
                parameters.add(room.getPrice().toString());
                roomDao.save(room, parameters, INSERT_ROOM_NO_PHOTO_KEY);

            } else {

                daoFactory.beginTx();
                PhotoDao photoDao = daoFactory.getPhotoDao();
                photoDao.addPhoto(room.getPhoto(), parameters, INSERT_PHOTO_KEY);
                parameters.add(String.valueOf(room.getNumber()));
                parameters.add(String.valueOf(room.getBed().getBed()));
                parameters.add(room.getRoomType().getRoomType());
                parameters.add(room.getPrice().toString());
                parameters.add(room.getPhoto().getId().toString());
                roomDao.save(room, parameters, INSERT_ROOM_WITH_PHOTO_KEY);
                daoFactory.commit();

            }
        } catch (NonUniqueFieldException e) {

            throw new RoomExistException(e);

        } catch (DaoException e) {

            throw new ServiceException(e);
        }
        return room;
    }
}