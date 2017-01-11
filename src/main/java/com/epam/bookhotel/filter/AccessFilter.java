package com.epam.bookhotel.filter;

import com.epam.bookhotel.entity.User;
import com.epam.bookhotel.entity.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.epam.bookhotel.constant.Constants.ACTION;
import static com.epam.bookhotel.constant.Constants.USER;

/**
 * Filter restrict actions with application for users without special rights.
 */

public class AccessFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);
    private static final String ANONIM_ACTION_FILE_NAME = "anonim-actions.properties";
    private static final String USER_ACTION_FILE_NAME = "user-actions.properties";
    private static final String MANAGER_ACTION_FILE_NAME = "manager-actions.properties";
    private static final String ALL_ACTION_FILE_NAME = "all-actions.properties";
    private List<String> anonimActionList = new ArrayList<>();
    private List<String> userActionList = new ArrayList<>();
    private List<String> managerActionList = new ArrayList<>();
    private List<String> allActionList = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        try {
            anonimActionList = getListFromFile(ANONIM_ACTION_FILE_NAME);
            userActionList = getListFromFile(USER_ACTION_FILE_NAME);
            managerActionList = getListFromFile(MANAGER_ACTION_FILE_NAME);
            allActionList = getListFromFile(ALL_ACTION_FILE_NAME);
        } catch (IOException e) {
            logger.error("Unable to open and load access actions from file.", e);
        }
    }


    /**
     * Fill the special access lists by actions
     *
     * @param fileName file consist lists of actions
     * @return the list of actions
     * @throws IOException if file not found
     */
    private List<String> getListFromFile(String fileName) throws IOException {

        List<String> list = new ArrayList<>();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //read action from received request
        String actionName = req.getParameter(ACTION);

        final User user = (User) req.getSession().getAttribute(USER);

        //get list with actions for user by his role
        List<String> actionList = getActionList(user);

        //check if action consist in list with all action
        if (!allActionList.contains((actionName))) {
            //if no, send on page with 404 error
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            logger.debug("The requested action {} not found", actionName);
            return;
        } else {
            //check if user has access to this action
            if (!actionList.contains(actionName)) {
                //if no, send user on page with 403 error code
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                logger.debug("Not authorized attempt to application, action \"{}\" from user \"{}\"", actionName, user);
                return;
            }
        }

        filterChain.doFilter(req, resp);
    }

    /**
     * Check user role and get list with actions for that role
     *
     * @param user the user who send request for action
     * @return the list with actions
     */
    private List<String> getActionList(User user) {

        if (user == null) return anonimActionList;
        if (UserType.USER.toString().equals(user.getRole().getRole().toString())) return userActionList;
        if (UserType.MANAGER.toString().equals(user.getRole().getRole().toString())) return managerActionList;
        return anonimActionList;
    }

    @Override
    public void destroy() {

    }
}
