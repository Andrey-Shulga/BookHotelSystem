package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SelectRoomAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SelectRoomAction.class);
    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";
    private static final String ORDER_ID_PARAMETER = "orderId";
    private static final String ROOM_ID_PARAMETER = "roomId";
    private static final String BLANK_FIELD_ERROR_MESSAGE = "blank.field.message";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String BLANK = "";
    private static final String REDIRECT = "redirect:/do/?action=show-manager-order-list";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        logger.debug("order {}", req.getParameter(ORDER_ID_PARAMETER));
        if (req.getParameter(ORDER_ID_PARAMETER).equals(BLANK)) {
            req.setAttribute(ORDER_ID_PARAMETER + ERROR_MESSAGE_SUFFIX, BLANK_FIELD_ERROR_MESSAGE);
            return REDIRECT;
        }
        if (req.getParameter(ROOM_ID_PARAMETER).equals(BLANK)) {
            req.setAttribute(ROOM_ID_PARAMETER + ERROR_MESSAGE_SUFFIX, BLANK_FIELD_ERROR_MESSAGE);
            return REDIRECT;
        }
        String orderId = req.getParameter(ORDER_ID_PARAMETER);
        String roomId = req.getParameter(ROOM_ID_PARAMETER);

        return REDIRECT;



    }
}
