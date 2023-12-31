package com.ts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ts.security.CustomUserDetailsService;
import com.ts.security.JwtAuthenticationEntryPoint;
import com.ts.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
                                "/**/*.html", "/**/*.css", "/**/*.js", "/showMemberOrder/**")
                        .permitAll()
                        .antMatchers("/app/**").permitAll()
                        .antMatchers("/ws/**").permitAll()
                        .antMatchers("/api/auth/**").permitAll()
                        .antMatchers("/api/ticket/**").permitAll()
                        .antMatchers("/ticket/management/**").permitAll()
                        .antMatchers("/user/management/**").permitAll()
                        .antMatchers("/api/roles").permitAll()
                        .antMatchers("/api/priorities").permitAll()
                        .antMatchers("/api/web").permitAll()
                        .antMatchers("/api/socket").permitAll()
                        .antMatchers("/api/ticket/complaint").permitAll()
                        .antMatchers("/api/ticket/{ticketId}/comment").permitAll()
                        .antMatchers("/api/ticket/**").permitAll()
                        .antMatchers("/api/categories").permitAll()
                        .antMatchers("/ticket/management/{id}/resolved").permitAll()
                        .antMatchers("/ticket/management/{id}/assigned").permitAll()
                        .antMatchers("/ticket/management/internal-user-tickets").permitAll()
                        .antMatchers("/user/management/allTickets").permitAll()
                        .antMatchers("/ticket/management/assignTicket/{ticketId}").permitAll()
                        .antMatchers("/api/web").permitAll()
                        .antMatchers("/api/message/convo").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
