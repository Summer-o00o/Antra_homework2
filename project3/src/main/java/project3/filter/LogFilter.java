package project3.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class LogFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("Request successfully sent to project3");
        // Pass the request/response to the next filter or resource
        chain.doFilter(request, response);
        logger.info("Response successfully sent from project3");
    }
}