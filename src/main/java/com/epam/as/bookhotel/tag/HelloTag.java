package com.epam.as.bookhotel.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HelloTag extends TagSupport {

    private static final String BLANK = "";
    private String login;

    public void setRole(String role) {
        this.login = role;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String welcomeMessage;
            if (!login.equals(BLANK)) {
                String HELLO = "Hello, ";
                welcomeMessage = HELLO + login;
                pageContext.getOut().write(welcomeMessage);
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
