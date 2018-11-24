package com.daniel.popek.thesis.app.communication.config;

import com.daniel.popek.thesis.app.persistence.repository.DesignerRepository;
import com.daniel.popek.thesis.app.domain.service.utils.IAuthenticationFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;


//@Configuration
@Component
public class SecurityConfig {

    @Configuration
    public static class FrontendConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        IAuthenticationFilterService authenticationFilterService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/managment/**")
                .addFilterBefore(
                        new AuthenticationFilterManagment(authenticationFilterService), BasicAuthenticationFilter.class).csrf().disable();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers("/authentication/**");
        }
    }

    @Order(1)
    @Configuration
    public static class ApiConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        DesignerRepository designerRepository;

        @Autowired
        IAuthenticationFilterService authenticationFilterService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .addFilterBefore(
                            new AuthenticationFilterAPI(authenticationFilterService), BasicAuthenticationFilter.class).csrf().disable();

        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);
        }
    }
}
