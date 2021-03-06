package com.epam.bookhotel.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Custom tag for output current user's login on page
 */

public class HelloTag extends TagSupport {

    private static final String BLANK = "";
    private String login;

    public void setRole(String role) {
        this.login = role;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            if (!login.equals(BLANK)) {

                pageContext.getOut().write(login);
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
