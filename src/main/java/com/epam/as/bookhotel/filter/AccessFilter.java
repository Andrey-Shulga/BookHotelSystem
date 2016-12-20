package com.epam.as.bookhotel.filter;

import com.epam.as.bookhotel.model.User;
import com.epam.as.bookhotel.model.UserType;
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

public class AccessFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);
    private static final String ACTION_PARAMETER_NAME = "action";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ANONIM_ACTION_FILE_NAME = "anonim-actions.properties";
    private static final String USER_ACTION_FILE_NAME = "user-actions.properties";
    private static final String MANAGER_ACTION_FILE_NAME = "manager-actions.properties";
    private static final String ACCESS_ERROR_MESSAGE = "Access denied";
    private List<String> anonimActionList = new ArrayList<>();
    private List<String> userActionList = new ArrayList<>();
    private List<String> managerActionList = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        try {
            anonimActionList = getListFromFile(ANONIM_ACTION_FILE_NAME);
            userActionList = getListFromFile(USER_ACTION_FILE_NAME);
            managerActionList = getListFromFile(MANAGER_ACTION_FILE_NAME);
        } catch (IOException e) {
            logger.error("Unable to open and load access actions from file.", e);
        }
    }

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
        String actionName = req.getParameter(ACTION_PARAMETER_NAME);

        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        List<String> actionList = getActionList(user);
        if (!actionList.contains(actionName)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, ACCESS_ERROR_MESSAGE);
            logger.debug("Not authorized attempt to application content from user {}", user);
            return;
        }

        filterChain.doFilter(req, resp);
    }

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
