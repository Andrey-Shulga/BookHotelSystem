package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action show page with all orders for user with manager rights in application
 */

public class ShowAllOrdersManagerAction implements Action {

    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";
    private static final String ORDER_LIST_ATTRIBUTE = "orders";
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String ALL_ORDER_LIST_JSP = "manager_allorder_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        OrderService orderService = new OrderService();
        try {
            List<Order> orderList = orderService.findAllOrders(new Order());
            req.setAttribute(ORDER_LIST_ATTRIBUTE, orderList);
        } catch (ServiceException e) {
            req.setAttribute(ORDER_LIST_ATTRIBUTE + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        return ALL_ORDER_LIST_JSP;
    }
}
