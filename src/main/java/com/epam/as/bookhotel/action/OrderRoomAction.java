package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Bed;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.model.RoomType;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.service.OrderService;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class OrderRoomAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(OrderRoomAction.class);
    private static final String LOGIN_FORM = "login";
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
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {

        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        FormValidator orderFormValidator = new FormValidator();
        Map<String, List<String>> fieldErrors = orderFormValidator.validate(ORDER_FORM, req);
        orderFormValidator.checkParameterOnNull(BED, req);
        orderFormValidator.checkParameterOnNull(ROOM_TYPE, req);

        if (!fieldErrors.isEmpty()) {
            orderFormValidator.setErrorToRequest(req);
            return ORDER_FORM;
        }
        logger.debug("Form's parameters are valid.");

        User user = (User) req.getSession().getAttribute(USER);

        String firstName = req.getParameter(FIRST_NAME);
        String lastName = req.getParameter(LAST_NAME);
        String email = req.getParameter(EMAIL);
        String phone = req.getParameter(PHONE);
        String checkIn = req.getParameter(CHECK_IN);
        String checkOut = req.getParameter(CHECK_OUT);
        LocalDate checkInDate = getLocalDate(checkIn);
        LocalDate checkOutDate = getLocalDate(checkOut);
        Bed bed = new Bed(Integer.parseInt(req.getParameter(BED)));
        RoomType roomType = new RoomType(req.getParameter(ROOM_TYPE));

        Order order = new Order(user, firstName, lastName, email, phone, checkInDate, checkOutDate, bed, roomType);

        OrderService orderService = new OrderService();
        try {
            orderService.makeOrder(order);
        } catch (JdbcDaoException e) {
            req.setAttribute(ORDER_FORM + ERROR_MESSAGE_SUFFIX, e.getMessage());
            return ORDER_FORM;
        }

        return REDIRECT;
    }

    private LocalDate getLocalDate(String date) {
        String datePattern = "dd/MM/uuuu";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDate.parse(date, formatter);
    }
}

