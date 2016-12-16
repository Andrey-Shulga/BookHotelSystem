package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ServiceException;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowNewOrdersManagerAction implements Action {

    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";
    private static final String ORDER_LIST_ATTRIBUTE = "orders";

    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String MANAGER_ORDER_LIST = "manager_order_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {

        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        OrderService orderService = new OrderService();
        try {
            List<Order> orderList = orderService.findAllOrdersByStatusUnconfirmed(new Order());
            req.setAttribute(ORDER_LIST_ATTRIBUTE, orderList);
        } catch (ServiceException e) {
            req.setAttribute(ORDER_LIST_ATTRIBUTE + ERROR_MESSAGE_SUFFIX, e.getMessage());
        }

        return MANAGER_ORDER_LIST;
    }
}
