package com.epam.as.bookhotel.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for handling errors like 403, 404, 500 and forwarding on proper error page
 */

@WebServlet(name = "ErrorHandlerServlet", urlPatterns = "/ErrorHandler")
public class ErrorHandlerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerServlet.class);
    private static final String PATH_TO_JSP = "/WEB-INF/jsp/";
    private static final String FILE_JSP = ".jsp";
    private static final String ERROR_STATUS_CODE_ATTRIBUTE = "javax.servlet.error.status_code";
    private static final String EXCEPTION_CODE_ATTRIBUTE = "javax.servlet.error.exception";
    private static final String ERROR_PAGE = "error";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer errorStatusCode = (Integer) req.getAttribute(ERROR_STATUS_CODE_ATTRIBUTE);
        Throwable exception = (Throwable) req.getAttribute(EXCEPTION_CODE_ATTRIBUTE);
        logger.error("Received Error with code {}, will be forwarded to proper error page.\nException: ", errorStatusCode, exception);
        req.getRequestDispatcher(PATH_TO_JSP + ERROR_PAGE + errorStatusCode + FILE_JSP).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
