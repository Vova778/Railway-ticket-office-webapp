package com.railway.ticket.office.webapp.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class NumberFormatterTag extends SimpleTagSupport {
    private String format;
    private String number;

    @Override
    public void doTag() throws JspException, IOException {
        try {
            if(!format.equals("")){
                number=number.replace(".",",");
            }
            getJspContext().getOut().write(number);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
