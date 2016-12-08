package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class OrderRoomAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(OrderRoomAction.class);
    private static final String ORDER_FORM = "order_form";
    private static final String REDIRECT = "redirect:/do/?action=show-user-order-list";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "firstName";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String CHECK_IN = "checkIn";
    private static final String CHECK_OUT = "checkOut";
    private static final String BED = "bed";
    private static final String ROOM_TYPE = "roomType";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        FormValidator orderFormValidator = new FormValidator();
        Map<String, List<String>> fieldErrors = orderFormValidator.validate(ORDER_FORM, req);
        if (!fieldErrors.isEmpty()) {
            orderFormValidator.setErrorToRequest(req);
            return ORDER_FORM;
        }
        logger.debug("Form's parameters are valid.");

        String firstName = req.getParameter(FIRST_NAME);
        String lastName = req.getParameter(LAST_NAME);
        String email = req.getParameter(EMAIL);
        String phone = req.getParameter(PHONE);
        String checkIn = req.getParameter(CHECK_IN);
        String checkOut = req.getParameter(CHECK_OUT);
        String bed = req.getParameter(BED);
        String roomType = req.getParameter(ROOM_TYPE);

        Order order = new Order(firstName, lastName, email, phone, checkIn, checkOut, Integer.parseInt(bed), roomType);

        return REDIRECT;
    }
}
