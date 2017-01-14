package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Room;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.bookhotel.constant.ConstantsHolder.*;

/**
 * Action show page with all rooms in hotel for user with manager rights.
 */

public class ShowAllRoomsManagerAction implements Action {

    private static final String ROOM_LIST = "manager_room_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        final User user = (User) req.getSession().getAttribute(USER);
        RoomService roomService = new RoomService();
        try {
            final List<Room> roomList = roomService.findAllRooms(new Room(), user);
            req.setAttribute(ROOMS, roomList);
        } catch (ServiceException e) {
            req.setAttribute(ROOMS + ERROR_MESSAGES_POSTFIX, e.getMessage());
        }

        ShowOrderFormAction action = new ShowOrderFormAction();
        action.execute(req, res);

        return ROOM_LIST;
    }
}
