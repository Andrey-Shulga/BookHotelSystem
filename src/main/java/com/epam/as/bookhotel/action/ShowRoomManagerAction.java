package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action show page with all rooms in hotel for user with manager rights.
 */

public class ShowRoomManagerAction implements Action {

    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";
    private static final String ROOMS_LIST_ATTRIBUTE = "rooms";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String ROOM_LIST_JSP = "manager_room_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        RoomService roomService = new RoomService();
        try {
            List<Room> roomList = roomService.findAllRooms(new Room());
            req.setAttribute(ROOMS_LIST_ATTRIBUTE, roomList);
        } catch (ServiceException e) {
            req.setAttribute(ROOMS_LIST_ATTRIBUTE + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        return ROOM_LIST_JSP;
    }
}
