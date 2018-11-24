package com.daniel.popek.thesis.app.config;



import com.daniel.popek.thesis.app.service.utils.IAuthenticationFilterService;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationFilterAPI extends GenericFilterBean {

    private IAuthenticationFilterService authenticationFilterService;


    AuthenticationFilterAPI(IAuthenticationFilterService service)
    {
        this.authenticationFilterService=service;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, No-Auth, Accept, X-Requested-With, remember-me");

        if(authenticationFilterService.apiHeaderIsUnauthorized(httpServletRequest.getHeader("Authorization"))){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
