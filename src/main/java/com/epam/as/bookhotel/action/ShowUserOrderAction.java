package com.epam.as.bookhotel.action;

import com.epam.as.bookhotel.exception.ConnectionPoolException;
import com.epam.as.bookhotel.exception.JdbcDaoException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.model.Order;
import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserOrderAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ShowUserOrderAction.class);
    private static final String USER = "user";
    private static final String LOGIN_FORM = "login";
    private static final String ORDER_LIST_ATTRIBUTE = "orders";
    private static final String USER_ORDER_LIST = "user_order_list";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PropertyManagerException, ValidatorException, ConnectionPoolException, JdbcDaoException {
        if (req.getSession(false).getAttribute(USER) == null) return LOGIN_FORM;
        User user = (User) req.getSession(false).getAttribute(USER);
        Order order = new Order();
        order.setUserId(user.getId());
        OrderService orderService = new OrderService();
        List<Order> orderList = orderService.findOrdersByUserId(order);
        req.setAttribute(ORDER_LIST_ATTRIBUTE, orderList);

        return USER_ORDER_LIST;
    }
}
