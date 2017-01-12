package com.epam.bookhotel.service;

import com.epam.bookhotel.dao.DaoFactory;
import com.epam.bookhotel.dao.RoomTypeDao;
import com.epam.bookhotel.entity.RoomType;
import com.epam.bookhotel.exception.DaoException;
import com.epam.bookhotel.exception.ServiceException;

import java.util.List;

/**
 * Service serves operation with entity RoomType
 */

public class RoomTypeService extends ParentService {

    private static final String FIND_ALL_ROOM_TYPES = "find.all.room.type";

    /**
     * Search all room types
     *
     * @param roomType entity for searching
     * @param locale   user's locale for searching localization values
     * @return the list of found room types
     * @throws ServiceException if any exception in service occurred
     */
    public List<RoomType> findAllRoomTypes(RoomType roomType, String locale) throws ServiceException {

        List<RoomType> roomTypeList;
        try (DaoFactory daoFactory = DaoFactory.createJdbcDaoFactory()) {
            RoomTypeDao bedDao = daoFactory.getRoomTypeDao();
            roomTypeList = bedDao.findAllByParameters(roomType, parameters, FIND_ALL_ROOM_TYPES, locale);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomTypeList;
    }
}
