package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.model.RoomType;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.service.OrderService;
import com.epam.as.bookhotel.util.DateConverter;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * User action for booking room in hotel.
 * Save user's order in database.
 */

public class OrderRoomAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(OrderRoomAction.class);
    private static final String ORDER_FORM = "order_form";
    private static final String REDIRECT = "redirect:/do/?action=show-user-order-list";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String USER = "user";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String CHECK_IN = "checkIn";
    private static final String CHECK_OUT = "checkOut";
    private static final String BED = "bed";
    private static final String ROOM_TYPE = "roomType";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        if (checkForm(req, ORDER_FORM)) return ORDER_FORM;
        logger.debug("Form's parameters are valid.");

        User user = (User) req.getSession().getAttribute(USER);

        String firstName = req.getParameter(FIRST_NAME);
        String lastName = req.getParameter(LAST_NAME);
        String email = req.getParameter(EMAIL);
        String phone = req.getParameter(PHONE);
        String checkIn = req.getParameter(CHECK_IN);
        String checkOut = req.getParameter(CHECK_OUT);
        DateConverter converter = new DateConverter();
        Date checkInDate = converter.getStrToDate(checkIn);
        Date checkOutDate = converter.getStrToDate(checkOut);
        Bed bed = new Bed(Integer.parseInt(req.getParameter(BED)));
        RoomType roomType = new RoomType(req.getParameter(ROOM_TYPE));

        Order order = new Order(user, firstName, lastName, email, phone, checkInDate, checkOutDate, bed, roomType);

        OrderService orderService = new OrderService();
        try {
            orderService.makeOrder(order);
        } catch (ServiceException e) {
            req.setAttribute(ORDER_FORM + ERROR_MESSAGE_SUFFIX, e.getMessage());
            return ORDER_FORM;
        }

        logger.debug("Make order action success.");
        return REDIRECT;
    }

    boolean checkForm(HttpServletRequest req, String validateForm) throws ActionException {

        boolean checkResult = false;
        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(validateForm, req);

            //check if form's dropdown list item not selected
            validator.checkDropDownListOnSelect(BED, req);
            validator.checkDropDownListOnSelect(ROOM_TYPE, req);
            if (validator.hasFieldsErrors(req, fieldErrors)) {
                checkResult = true;
            }

        } catch (ValidatorException e) {
            throw new ActionException(e);
        }
        return checkResult;
    }

}

