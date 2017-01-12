package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Order;
import com.epam.bookhotel.entity.Room;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.exception.ValidatorException;
import com.epam.bookhotel.service.OrderService;
import com.epam.bookhotel.util.SessionHelper;
import com.epam.bookhotel.util.ValidatorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.bookhotel.constant.ConstantsHolder.*;

/**
 * Action selects room for order by order id and room number
 * Change status order's status "unconfirmed"->"confirmed".
 */

public class SelectRoomManagerAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SelectRoomManagerAction.class);
    private static final String MANAGER_ORDER_LIST_FORM = "manager_order_list";
    private static final String REDIRECT_ORDER_LIST = "redirect:/do/?action=show-manager-order-list";
    private static final String ORDER_ID = "orderId";
    private static final String ROOM_NUMBER = "roomNumber";
    private static final String CONFIRM = "confirm";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        String orderId = req.getParameter(ORDER_ID);
        SessionHelper.saveParamToSession(req, ORDER_ID, orderId);
        String roomNumber = req.getParameter(ROOM_NUMBER);
        SessionHelper.saveParamToSession(req, ROOM_NUMBER, roomNumber);

        try {
            if (ValidatorHelper.checkForm(req, MANAGER_ORDER_LIST_FORM)) return REDIRECT_ORDER_LIST;
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }

        logger.debug("Form's parameters are valid.");
        final User user = (User) req.getSession().getAttribute(USER);

        final Room room = new Room();
        room.setNumber(Integer.parseInt(roomNumber));
        final Order order = new Order();
        order.setId(Integer.parseInt(orderId));
        order.setRoom(room);
        order.setUser(user);

        OrderService orderService = new OrderService();
        try {
            orderService.confirmRoomForOrder(order);

            //remove list of room from page after success order confirmation
            req.getSession().removeAttribute(ROOMS);
        } catch (ServiceException e) {
            req.getSession().setAttribute(CONFIRM + ERROR_MESSAGES_POSTFIX, e.getMessage());
        }

        return REDIRECT_ORDER_LIST;
    }

}
