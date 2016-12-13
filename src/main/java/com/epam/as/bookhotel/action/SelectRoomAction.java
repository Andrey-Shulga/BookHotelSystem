package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.ConfirmationOrder;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.service.ConfirmationOrderService;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class SelectRoomAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SelectRoomAction.class);
    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";
    private static final String ORDER_ID_PARAMETER = "orderId";
    private static final String ROOM_ID_PARAMETER = "roomId";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String MANAGER_ORDER_LIST_FORM = "manager_order_list";
    private static final String REDIRECT = "redirect:/do/?action=show-manager-order-list";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        if (req.getParameter(ORDER_ID_PARAMETER) == null) return MANAGER_ORDER_LIST_FORM;
        FormValidator confirmationOrderFormValidator = new FormValidator();
        Map<String, List<String>> fieldErrors = confirmationOrderFormValidator.validate(MANAGER_ORDER_LIST_FORM, req);
        if (!fieldErrors.isEmpty()) {
            confirmationOrderFormValidator.setErrorToRequest(req);
            logger.debug("NOT VALID");
            return REDIRECT;
        }
        logger.debug("Form's parameters are valid.");
        String orderId = req.getParameter(ORDER_ID_PARAMETER);
        String roomId = req.getParameter(ROOM_ID_PARAMETER);

        Order order = new Order();
        order.setId(Integer.parseInt(orderId));
        Room room = new Room();
        room.setId(Integer.parseInt(roomId));
        ConfirmationOrder confirmationOrder = new ConfirmationOrder(order, room);
        ConfirmationOrderService service = new ConfirmationOrderService();
        service.confirmOrder(confirmationOrder);

        return REDIRECT;



    }
}
