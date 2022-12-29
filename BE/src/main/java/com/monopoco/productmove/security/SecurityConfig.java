package com.monopoco.productmove.security;

import com.monopoco.productmove.filter.CustomerAuthenticationFilter;
import com.monopoco.productmove.filter.CustomerAuthorizationFilter;
import com.monopoco.productmove.filter.SimpleCORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    SimpleCORSFilter simpleCORSFilter() {
        SimpleCORSFilter corsFilter = new SimpleCORSFilter();
        return corsFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.cors().disable();
//                http.csrf().and().cors().disable();
        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:3000"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        }).and();
        http.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed"));
        http.authorizeHttpRequests().antMatchers("/login/**").permitAll();
        http.authorizeHttpRequests().antMatchers("/api/v1/users/**").hasAuthority("ADMIN");
//        http.authorizeHttpRequests().antMatchers("/api/v1/branches/**").hasAuthority("ADMIN");
//        http.authorizeHttpRequests().antMatchers("/api/v1/branches/products/**").hasAnyAuthority("FACTORY");

        http.authorizeHttpRequests().anyRequest().authenticated();

        http.addFilter(new CustomerAuthenticationFilter(authenticationManagerBean()));
//        http.addFilterAfter(new SimpleCORSFilter(), Filter.class);
        http.addFilterBefore(new CustomerAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
