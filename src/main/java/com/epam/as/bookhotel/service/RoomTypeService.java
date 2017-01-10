package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.RoomTypeDao;
import com.epam.as.bookhotel.entity.RoomType;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Service serves operation with entity RoomType
 */

public class RoomTypeService extends ParentService {

    private static final String FIND_ALL_ROOM_TYPES = "find.all.room.type";
    private final List<Object> parameters = new ArrayList<>();

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
            roomTypeList = bedDao.findByParameters(roomType, parameters, FIND_ALL_ROOM_TYPES, locale);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomTypeList;
    }
}
