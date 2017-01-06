package com.epam.as.bookhotel.servlet;


import com.epam.as.bookhotel.action.Action;
import com.epam.as.bookhotel.action.ActionFactory;
import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.ActionFactoryException;
import com.epam.as.bookhotel.util.ValidatorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main controller servlet for serving users actions
 */

@WebServlet(name = "FrontControllerServlet", urlPatterns = "/do/*")
@MultipartConfig
public class FrontControllerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(FrontControllerServlet.class);
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String ACTION_ATTR = "action";
    private static final String PATH_TO_JSP = "/WEB-INF/jsp/";
    private static final String FILE_JSP = ".jsp";
    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {

        actionFactory = new ActionFactory();
        try {
            actionFactory.loadActions();
        } catch (ActionFactoryException e) {
            logger.error("Action Factory error in controller occurred", e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String actionName = getActionName(req);
        try {
            Action action = actionFactory.getAction(actionName);
            logger.debug("Received {} request with command: \"{}\", get action: {}", req.getMethod(), actionName, action.getClass().getSimpleName());
            String view = action.execute(req, resp);
            proceedTo(view, req, resp);
        } catch (ActionException | ActionFactoryException e) {
            logger.error("Exception in controller occurred", e);
        }
    }

    private void proceedTo(String view, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (view.startsWith(REDIRECT_PREFIX)) {
            resp.sendRedirect(view.substring(REDIRECT_PREFIX.length()));
        } else {
            req.getRequestDispatcher(PATH_TO_JSP + view + FILE_JSP).forward(req, resp);
            //delete errors from previous validation
            ValidatorHelper validatorHelper = new ValidatorHelper();
            validatorHelper.deleteValidatorsErrorsFromSession(req);
        }

    }

    /**
     * Get action name value from request
     *
     * @param req HTTP request
     * @return action
     */
    private String getActionName(HttpServletRequest req) {
        return req.getParameter(ACTION_ATTR);
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
