package com.epam.as.bookhotel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomExistException extends ServiceException {

    private static final Logger logger = LoggerFactory.getLogger(RoomExistException.class);
    private static final String ROOM_EXIST_ERROR_MSG = "add.room.error.message.exist";

    public RoomExistException(Exception e) {

        super(ROOM_EXIST_ERROR_MSG, e);
        logger.error("Room is already exist.", e);
    }
}
