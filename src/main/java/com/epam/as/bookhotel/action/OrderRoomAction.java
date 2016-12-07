package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
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

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        FormValidator registerFormValidator = new FormValidator();
        Map<String, List<String>> fieldErrors = registerFormValidator.validate(ORDER_FORM, req);
        if (!fieldErrors.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
                req.setAttribute(entry.getKey() + ERROR_MESSAGE_SUFFIX, entry.getValue());
                for (String errorMessage : entry.getValue()) {
                    logger.debug("In filed \"{}\" found error message \"{}\"", entry.getKey(), errorMessage);
                }
            }
            return ORDER_FORM;
        }
        logger.debug("Form's parameters are valid.");

        return REDIRECT;
    }
}
