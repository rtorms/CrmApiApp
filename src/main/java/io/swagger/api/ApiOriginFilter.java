package io.swagger.api;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-12T11:24:44.655297665Z[GMT]")
public class ApiOriginFilter implements Filter {
	
	
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, POST, DELETE, PUT");
		res.addHeader("Access-Control-Allow-Headers",
				"Content-Type, Authorization, x-amzn-apigateway-api-id, Accept, User-Agent, X-Amzn-Trace-Id");

				
		chain.doFilter(request, response);
		
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
