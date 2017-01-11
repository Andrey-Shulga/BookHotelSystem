package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Bed;
import com.epam.bookhotel.entity.Order;
import com.epam.bookhotel.entity.RoomType;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.exception.ValidatorException;
import com.epam.bookhotel.service.OrderService;
import com.epam.bookhotel.util.DateConverter;
import com.epam.bookhotel.util.SessionHelper;
import com.epam.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.epam.bookhotel.constant.Constants.*;

/**
 * User action for booking room in hotel.
 * Save user's order in database.
 */

public class OrderRoomAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(OrderRoomAction.class);
    private static final String ORDER_FORM = "order_form";
    private static final String REDIRECT_ORDER_FORM = "redirect:/do/?action=show-order-form";
    private static final String REDIRECT_ORDER_LIST = "redirect:/do/?action=show-user-order-list";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(ORDER_FORM, req);

            //check if form's dropdown list item not selected
            validator.checkDropDownListOnSelect(ROOM_BED, req);
            validator.checkDropDownListOnSelect(ROOM_TYPE, req);
            if (validator.hasFieldsErrors(req, fieldErrors)) return REDIRECT_ORDER_FORM;

        } catch (ValidatorException e) {
            throw new ActionException(e);
        }

        logger.debug("Form's parameters are valid.");

        final User user = (User) req.getSession().getAttribute(USER);

        String firstName = req.getParameter(FIRST_NAME);
        SessionHelper.saveParamToSession(req, FIRST_NAME, firstName);
        String lastName = req.getParameter(LAST_NAME);
        SessionHelper.saveParamToSession(req, LAST_NAME, lastName);
        String email = req.getParameter(EMAIL);
        SessionHelper.saveParamToSession(req, EMAIL, email);
        String phone = req.getParameter(PHONE);
        SessionHelper.saveParamToSession(req, PHONE, phone);
        String checkIn = req.getParameter(CHECK_IN);
        SessionHelper.saveParamToSession(req, CHECK_IN, checkIn);
        String checkOut = req.getParameter(CHECK_OUT);
        SessionHelper.saveParamToSession(req, CHECK_OUT, checkOut);
        Date checkInDate = DateConverter.getStrToDate(checkIn);
        Date checkOutDate = DateConverter.getStrToDate(checkOut);
        Bed bed = new Bed(Integer.parseInt(req.getParameter(ROOM_BED)));
        RoomType roomType = new RoomType(req.getParameter(ROOM_TYPE));

        final Order order = new Order(user, firstName, lastName, email, phone, checkInDate, checkOutDate, bed, roomType);

        OrderService orderService = new OrderService();
        Order newOrder;
        try {
            newOrder = orderService.makeOrder(order);
        } catch (ServiceException e) {
            req.getSession().setAttribute(ORDER_FORM + ERROR_MESSAGES_POSTFIX, e.getMessage());
            return REDIRECT_ORDER_FORM;
        }

        logger.debug("Make order action success. {}", newOrder);
        return REDIRECT_ORDER_LIST;
    }

}

