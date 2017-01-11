package com.epam.bookhotel.service;

import com.epam.bookhotel.dao.DaoFactory;
import com.epam.bookhotel.dao.PhotoDao;
import com.epam.bookhotel.dao.RoomDao;
import com.epam.bookhotel.entity.Photo;
import com.epam.bookhotel.entity.Room;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.DaoException;
import com.epam.bookhotel.exception.NonUniqueFieldException;
import com.epam.bookhotel.exception.RoomExistException;
import com.epam.bookhotel.exception.ServiceException;

import java.util.List;

/**
 * Service serves operation with entity Room
 */
public class RoomService extends ParentService {

    private static final String FIND_ALL_ROOMS_KEY = "find.all.rooms";
    private static final String FIND_ALL_FREE_ROOMS_BY_DATE_KEY = "find.free.rooms.on.date.range";
    private static final String INSERT_ROOM_NO_PHOTO_KEY = "insert.room.no.photo";
    private static final String INSERT_ROOM_WITH_PHOTO_KEY = "insert.room.with.photo";
    private static final String INSERT_PHOTO_KEY = "insert.photo";

    /**
     * Search all room types
     *
     * @param room entity for searching
     * @param user for user locale
     * @return the list of found rooms
     * @throws ServiceException if any exception in service occurred
     */
    public List<Room> findAllRooms(Room room, User user) throws ServiceException {

        return getRoomsList(room, user, FIND_ALL_ROOMS_KEY);
    }

    /**
     * Search all free rooms on date range
     *
     * @param checkIn  date start
     * @param checkOut date end
     * @param user     for user locale
     * @return the list of found rooms
     * @throws ServiceException if any exception in service occurred
     */
    public List<Room> findAllFreeRoomsOnBookingDate(String checkIn, String checkOut, User user) throws ServiceException {

        parameters.add(checkIn);
        parameters.add(checkOut);
        parameters.add(checkIn);
        parameters.add(checkOut);
        return getRoomsList(new Room(), user, FIND_ALL_FREE_ROOMS_BY_DATE_KEY);
    }

    /**
     * General method for searching rooms
     *
     * @param room entity for searching
     * @param user for user locale
     * @param key property key for query
     * @return the list of found rooms
     * @throws ServiceException if any exception in service occurred
     */
    private List<Room> getRoomsList(Room room, User user, String key) throws ServiceException {

        List<Room> roomList;
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            RoomDao roomDao = daoFactory.getRoomDao();
            roomList = roomDao.findByParameters(room, parameters, key, user.getLocale().getLocaleName());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomList;
    }

    /**
     * Add new room.
     *
     * @param room entity for inserting
     * @return saved room with id
     * @throws ServiceException if any exception in service occurred
     */
    public Room addRoom(Room room) throws ServiceException {

        Room newRoom;
        Photo newPhoto;
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            //add new room without photo
            RoomDao roomDao = daoFactory.getRoomDao();
            if (room.getPhoto() == null) {
                parameters.add(room.getNumber());
                parameters.add(room.getBed().getBed());
                parameters.add(room.getRoomType().getRoomType());
                parameters.add(room.getPrice());
                newRoom = roomDao.save(room, parameters, INSERT_ROOM_NO_PHOTO_KEY);
                //add new room with photo
            } else {
                daoFactory.beginTx();
                PhotoDao photoDao = daoFactory.getPhotoDao();
                newPhoto = photoDao.addPhoto(room.getPhoto(), parameters, INSERT_PHOTO_KEY);
                parameters.add(room.getNumber());
                parameters.add(room.getBed().getBed());
                parameters.add(room.getRoomType().getRoomType());
                parameters.add(room.getPrice());
                parameters.add(newPhoto.getId());
                newRoom = roomDao.save(room, parameters, INSERT_ROOM_WITH_PHOTO_KEY);
                daoFactory.commit();
            }
        } catch (NonUniqueFieldException e) {

            throw new RoomExistException(e);

        } catch (DaoException e) {

            throw new ServiceException(e);
        }
        return newRoom;
    }
}