package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.entity.Bed;
import com.epam.as.bookhotel.entity.Order;
import com.epam.as.bookhotel.entity.RoomType;
import com.epam.as.bookhotel.entity.User;
import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.service.OrderService;
import com.epam.as.bookhotel.util.DateConverter;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User action for booking room in hotel.
 * Save user's order in database.
 */

public class OrderRoomAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(OrderRoomAction.class);
    private static final String ORDER_FORM = "order_form";
    private static final String REDIRECT_ORDER_FORM = "redirect:/do/?action=show-order-form";
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

        saveInputField(req);
        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(ORDER_FORM, req);

            //check if form's dropdown list item not selected
            validator.checkDropDownListOnSelect(BED, req);
            validator.checkDropDownListOnSelect(ROOM_TYPE, req);
            if (validator.hasFieldsErrors(req, fieldErrors)) return REDIRECT_ORDER_FORM;

        } catch (ValidatorException e) {
            throw new ActionException(e);
        }

        logger.debug("Form's parameters are valid.");

        final User user = (User) req.getSession().getAttribute(USER);

        String firstName = req.getParameter(FIRST_NAME);
        String lastName = req.getParameter(LAST_NAME);
        String email = req.getParameter(EMAIL);
        String phone = req.getParameter(PHONE);
        String checkIn = req.getParameter(CHECK_IN);
        String checkOut = req.getParameter(CHECK_OUT);
        final Date checkInDate = DateConverter.getStrToDate(checkIn);
        final Date checkOutDate = DateConverter.getStrToDate(checkOut);
        final Bed bed = new Bed(Integer.parseInt(req.getParameter(BED)));
        final RoomType roomType = new RoomType(req.getParameter(ROOM_TYPE));

        final Order order = new Order(user, firstName, lastName, email, phone, checkInDate, checkOutDate, bed, roomType);

        OrderService orderService = new OrderService();
        try {
            orderService.makeOrder(order);
        } catch (ServiceException e) {
            req.getSession().setAttribute(ORDER_FORM + ERROR_MESSAGE_SUFFIX, e.getMessage());
            return REDIRECT_ORDER_FORM;
        }

        logger.debug("Make order action success.");
        return REDIRECT;
    }

    private void saveInputField(HttpServletRequest req) {

        String firstName = req.getParameter(FIRST_NAME);
        req.getSession().setAttribute(FIRST_NAME, firstName);
        String lastName = req.getParameter(LAST_NAME);
        req.getSession().setAttribute(LAST_NAME, lastName);
        String email = req.getParameter(EMAIL);
        req.getSession().setAttribute(EMAIL, email);
        String phone = req.getParameter(PHONE);
        req.getSession().setAttribute(PHONE, phone);
        String checkIn = req.getParameter(CHECK_IN);
        req.getSession().setAttribute(CHECK_IN, checkIn);
        String checkOut = req.getParameter(CHECK_OUT);
        req.getSession().setAttribute(CHECK_OUT, checkOut);
    }

}

