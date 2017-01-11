package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Room;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action show page with all rooms in hotel for user with manager rights.
 */

public class ShowAllRoomsManagerAction implements Action {

    private static final String USER_ATTR_NAME = "user";
    private static final String ROOMS_LIST_ATTRIBUTE = "rooms";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String ROOM_LIST_JSP = "manager_room_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        final User user = (User) req.getSession().getAttribute(USER_ATTR_NAME);
        RoomService roomService = new RoomService();
        try {
            final List<Room> roomList = roomService.findAllRooms(new Room(), user);
            req.setAttribute(ROOMS_LIST_ATTRIBUTE, roomList);
        } catch (ServiceException e) {
            req.setAttribute(ROOMS_LIST_ATTRIBUTE + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        ShowOrderFormAction action = new ShowOrderFormAction();
        try {
            action.execute(req, res);
        } catch (ActionException e) {
            throw new ActionException(e);
        }

        return ROOM_LIST_JSP;
    }
}