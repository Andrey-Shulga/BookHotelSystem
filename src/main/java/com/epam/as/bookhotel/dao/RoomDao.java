package com.epam.as.bookhotel.dao;

import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.model.Room;

public interface RoomDao extends Dao<Room> {

    void addRoomWithPhoto(Room room) throws JdbcDaoException;
}
