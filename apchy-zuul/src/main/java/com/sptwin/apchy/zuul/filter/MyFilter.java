package com.sptwin.apchy.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class MyFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        logger.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object object = request.getParameter("token");
        if (null != object){
            return null;
        }
        logger.warn("token is empty");
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(401);
        try {
            requestContext.getResponse().getWriter().write("token is empty");
        }catch (Exception e){

        }
        return null;
    }
}
