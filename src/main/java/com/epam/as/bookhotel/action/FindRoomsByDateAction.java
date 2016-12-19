package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.service.RoomService;
import com.epam.as.bookhotel.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class FindRoomsByDateAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(FindRoomsByDateAction.class);
    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";
    private static final String ROOMS_LIST_ATTRIBUTE = "rooms";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String CHECK_IN_PARAMETER = "checkIn";
    private static final String CHECK_OUT_PARAMETER = "checkOut";
    private static final String REDIRECT = "redirect:/do/?action=show-manager-order-list";
    private static final String MANAGER_ORDER_FORM = "manager_order_list";
    private static final String SEARCH_BUTTON_PARAMETER = "search";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        if (req.getParameter(CHECK_IN_PARAMETER) == null) return MANAGER_ORDER_FORM;
        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(MANAGER_ORDER_FORM, req);
            if (!fieldErrors.isEmpty()) {
                validator.setErrorToRequest(req);
                return REDIRECT;
            }
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }

        logger.debug("Form's parameters are valid.");
        String checkIn = req.getParameter(CHECK_IN_PARAMETER);
        String checkOut = req.getParameter(CHECK_OUT_PARAMETER);

        RoomService roomService = new RoomService();
        try {
            List<Room> roomList = roomService.findAllFreeRoomsOnBookingDate(checkIn, checkOut);
            req.getSession().setAttribute(ROOMS_LIST_ATTRIBUTE, roomList);
        } catch (ServiceException e) {
            req.getSession().setAttribute(SEARCH_BUTTON_PARAMETER + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }
        return REDIRECT;
    }

}
