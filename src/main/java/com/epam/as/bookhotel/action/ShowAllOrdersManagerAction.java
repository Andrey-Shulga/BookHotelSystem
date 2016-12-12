package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.service.OrderService;
import com.epam.as.bookhotel.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllOrdersManagerAction implements Action {

    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";
    private static final String ORDER_LIST_ATTRIBUTE = "orders";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String ALL_ORDER_LIST_JSP = "manager_allorder_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        OrderService orderService = new OrderService();
        RoomService roomService = new RoomService();
        try {
            List<Order> orderList = orderService.findAllOrders(new Order());
            req.setAttribute(ORDER_LIST_ATTRIBUTE, orderList);
        } catch (JdbcDaoException e) {
            req.setAttribute(ORDER_LIST_ATTRIBUTE + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        return ALL_ORDER_LIST_JSP;
    }
}
