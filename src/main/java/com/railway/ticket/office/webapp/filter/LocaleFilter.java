package com.railway.ticket.office.webapp.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LocaleFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    private static final Logger log = LogManager.getLogger(LocaleFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        log.info("[LocaleFilter] Filter started {},  {}",
                request.getParameterMap(), response);

        if (req.getParameter("locale") != null) {
            req.getSession().setAttribute("lang",
                    req.getParameter("locale"));
        }

        chain.doFilter(request, response);
    }
}
