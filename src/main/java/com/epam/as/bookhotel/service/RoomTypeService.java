package com.epam.as.bookhotel.service;

import com.epam.as.bookhotel.dao.DaoFactory;
import com.epam.as.bookhotel.dao.RoomTypeDao;
import com.epam.as.bookhotel.exception.DaoException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.RoomType;

import java.util.ArrayList;
import java.util.List;

public class RoomTypeService extends ParentService {

    private static final String FIND_ALL_ROOM_TYPES = "find.all.room.type";
    private final List<Object> parameters = new ArrayList<>();

    public List<RoomType> findAllRoomTypes(RoomType roomType, String locale) throws ServiceException {

        List<RoomType> roomTypeList;
        try (DaoFactory daoFactory = DaoFactory.createFactory()) {
            RoomTypeDao bedDao = daoFactory.getRoomTypeDao();
            roomTypeList = bedDao.findByParameters(roomType, parameters, FIND_ALL_ROOM_TYPES, locale);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return roomTypeList;
    }
}
