package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Room;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.service.RoomService;
import com.epam.as.bookhotel.util.ValidatorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This action searches all free (not booked) rooms in the hotel on special date range.
 */

public class FindRoomsByDateManagerAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(FindRoomsByDateManagerAction.class);
    private static final String MANAGER_ORDER_LIST_FORM = "manager_order_list";
    private static final String USER_ATTR_NAME = "user";
    private static final String ROOMS_LIST_ATTRIBUTE = "rooms";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String CHECK_IN_PARAMETER = "checkIn";
    private static final String CHECK_OUT_PARAMETER = "checkOut";
    private static final String SEARCH_BUTTON_PARAMETER = "search";
    private static final String REDIRECT = "redirect:/do/?action=show-manager-order-list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        //check form's filed on errors
        ValidatorHelper validatorHelper = new ValidatorHelper();
        try {
            if (validatorHelper.checkForm(req, MANAGER_ORDER_LIST_FORM)) return REDIRECT;
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }


        logger.debug("Form's parameters are valid.");

        User user = (User) req.getSession().getAttribute(USER_ATTR_NAME);
        String checkIn = req.getParameter(CHECK_IN_PARAMETER);
        String checkOut = req.getParameter(CHECK_OUT_PARAMETER);

        RoomService roomService = new RoomService();
        try {
            List<Room> roomList = roomService.findAllFreeRoomsOnBookingDate(checkIn, checkOut, user);
            req.getSession().setAttribute(ROOMS_LIST_ATTRIBUTE, roomList);
        } catch (ServiceException e) {
            req.getSession().setAttribute(SEARCH_BUTTON_PARAMETER + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }
        return REDIRECT;
    }

}
