package com.epam.as.bookhotel.servlet;


import com.epam.as.bookhotel.action.Action;
import com.epam.as.bookhotel.action.ActionFactory;
import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
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

            actionFactory = new ActionFactory();
        try {
            actionFactory.loadActions();
        } catch (PropertyManagerException e) {
            logger.error("PropertyManagerException occurred", e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = getActionName(req);
        try {
            Action action = actionFactory.getAction(actionName);
            logger.debug("Received request with command: \"{}\", get action: {}", actionName, action.getClass().getSimpleName());
            String view = action.execute(req, resp);
        } catch (ActionException | PropertyManagerException | ValidatorException e) {
            logger.error("ActionException or PropertyManagerException or ValidatorException occurred", e);
        }
    }

    private String getActionName(HttpServletRequest req) {
        return req.getParameter("command");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
