package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.RoomStatus;
import com.epam.as.bookhotel.service.OrderService;
import com.epam.as.bookhotel.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowNewOrdersManagerAction implements Action {

    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";
    private static final String ORDER_LIST_ATTRIBUTE = "orders";
    private static final String ROOMS_LIST_ATTRIBUTE = "rooms";
    private static final String ROOM_FREE_STATUS = "free";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String ORDER_LIST_JSP = "manager_order_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {

        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        OrderService orderService = new OrderService();
        try {
            List<Order> orderList = orderService.findAllOrdersByStatus(new Order());
            req.setAttribute(ORDER_LIST_ATTRIBUTE, orderList);
        } catch (JdbcDaoException e) {
            req.setAttribute(ORDER_LIST_ATTRIBUTE + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        RoomService roomService = new RoomService();
        try {
            Room room = new Room();
            room.setRoomStatus(new RoomStatus(ROOM_FREE_STATUS));
            List<Room> roomList = roomService.findAllRoomsByStatus(room);
            req.setAttribute(ROOMS_LIST_ATTRIBUTE, roomList);
        } catch (JdbcDaoException e) {
            req.setAttribute(ROOMS_LIST_ATTRIBUTE + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        return ORDER_LIST_JSP;
    }
}
