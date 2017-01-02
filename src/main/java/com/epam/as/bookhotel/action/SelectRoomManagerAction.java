package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.service.OrderService;
import com.epam.as.bookhotel.util.ValidatorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action selects room for order by order id and room number
 * Change status order's status "unconfirmed"->"confirmed".
 */

public class SelectRoomManagerAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SelectRoomManagerAction.class);
    private static final String USER = "user";
    private static final String ORDER_ID_PARAMETER = "orderId";
    private static final String ROOM_ID_PARAMETER = "roomId";
    private static final String CONFIRM_BUTTON_PARAMETER = "confirm";
    private static final String ROOMS_LIST_ATTRIBUTE = "rooms";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String MANAGER_ORDER_LIST_FORM = "manager_order_list";
    private static final String REDIRECT = "redirect:/do/?action=show-manager-order-list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        ValidatorHelper validatorHelper = new ValidatorHelper();
        try {
            if (validatorHelper.checkForm(req, MANAGER_ORDER_LIST_FORM)) return REDIRECT;
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }

        logger.debug("Form's parameters are valid.");
        final User user = (User) req.getSession(false).getAttribute(USER);
        String orderId = req.getParameter(ORDER_ID_PARAMETER);
        String roomId = req.getParameter(ROOM_ID_PARAMETER);

        Room room = new Room();
        room.setNumber(Integer.parseInt(roomId));
        Order order = new Order();
        order.setId(Integer.parseInt(orderId));
        order.setRoom(room);
        order.setUser(user);

        OrderService orderService = new OrderService();
        try {
            orderService.confirmRoomForOrder(order);

            //remove list of room from page after success order confirmation
            req.getSession().removeAttribute(ROOMS_LIST_ATTRIBUTE);
        } catch (ServiceException e) {
            req.getSession().setAttribute(CONFIRM_BUTTON_PARAMETER + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        return REDIRECT;
    }

}
