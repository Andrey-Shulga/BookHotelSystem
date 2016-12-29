package com.epam.as.bookhotel.tag;

import com.epam.as.bookhotel.model.Order;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Custom tag for calculate total sum of field values
 */

public class FieldTotalSum extends TagSupport {


    private static final int ZERO = 0;
    private List orders;

    public List getOrders() {
        return orders;
    }

    public void setOrders(List orders) {
        this.orders = orders;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            BigDecimal sum = BigDecimal.valueOf(ZERO);
            for (Object order : orders) {
                Order o = (Order) order;
                sum = sum.add(o.getFullCost());
            }
            pageContext.getOut().print("" + sum);
            return SKIP_BODY;
        } catch (IOException e) {
            throw new JspException(e);
        }
    }
}
