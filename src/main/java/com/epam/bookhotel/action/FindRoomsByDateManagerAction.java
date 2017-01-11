package com.epam.bookhotel.action;

import com.epam.bookhotel.entity.Room;
import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.exception.ActionException;
import com.epam.bookhotel.exception.ServiceException;
import com.epam.bookhotel.exception.ValidatorException;
import com.epam.bookhotel.service.RoomService;
import com.epam.bookhotel.util.SessionHelper;
import com.epam.bookhotel.util.ValidatorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.bookhotel.constant.Constants.*;

/**
 * This action searches all free (not booked) rooms in the hotel on special date range.
 */

public class FindRoomsByDateManagerAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(FindRoomsByDateManagerAction.class);
    private static final String REDIRECT_ORDER_LIST = "redirect:/do/?action=show-manager-order-list";
    private static final String MANAGER_ORDER_LIST_FORM = "manager_order_list";
    private static final String SEARCH = "search";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        String checkIn = req.getParameter(CHECK_IN);
        SessionHelper.saveParamToSession(req, CHECK_IN, checkIn);
        String checkOut = req.getParameter(CHECK_OUT);
        SessionHelper.saveParamToSession(req, CHECK_OUT, checkOut);

        //check form's filed on errors
        try {
            if (ValidatorHelper.checkForm(req, MANAGER_ORDER_LIST_FORM)) return REDIRECT_ORDER_LIST;
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }
        logger.debug("Form's parameters are valid.");

        final User user = (User) req.getSession().getAttribute(USER);

        RoomService roomService = new RoomService();
        try {
            final List<Room> roomList = roomService.findAllFreeRoomsOnBookingDate(checkIn, checkOut, user);
            req.getSession().setAttribute(ROOMS, roomList);
        } catch (ServiceException e) {
            req.getSession().setAttribute(SEARCH + ERROR_MESSAGES_POSTFIX, e.getMessage());
        }
        return REDIRECT_ORDER_LIST;
    }

}
