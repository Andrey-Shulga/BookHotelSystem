package com.epam.as.bookhotel.servlet;


import com.epam.as.bookhotel.action.ActionFactory;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FrontControllerServlet", urlPatterns = "/do/*")
@MultipartConfig
public class FrontControllerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(FrontControllerServlet.class);
    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        try {
            actionFactory = new ActionFactory();
        } catch (PropertyManagerException e) {
            logger.error("Can't open file {} for reading properties.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = getActionName(req);
        logger.debug(actionName);
    }

    private String getActionName(HttpServletRequest req) {
        return req.getParameter("register");
    }
}
