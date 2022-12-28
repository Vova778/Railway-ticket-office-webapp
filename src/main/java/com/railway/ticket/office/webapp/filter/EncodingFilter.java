package com.railway.ticket.office.webapp.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;


@WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8")})
public class EncodingFilter implements Filter {
    private static final Logger log = LogManager.getLogger(EncodingFilter.class);
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("encoding");
        if (encoding == null) encoding = "UTF-8";
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("[EncodingFilter] Filter started {},  {}",
                servletRequest.getParameterMap(), servletResponse);

        if (servletRequest.getCharacterEncoding() == null) {
            servletRequest.setCharacterEncoding(encoding);
            log.info("[EncodingFilter] encoding: {}", encoding);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
