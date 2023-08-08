package com.juliomesquita.oauthserverteste.infrastructure.configurations;

import com.juliomesquita.oauthserverteste.domain.services.CustomOAuth2UserService;
import com.juliomesquita.oauthserverteste.domain.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private CustomOAuth2UserService oauthUserService;
    @Autowired
    private UserService userService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(request->
                        request
                                //.requestMatchers("/login", "/oauth/**").permitAll()
                                .anyRequest()
                                .authenticated())
                .oauth2Login(auth -> auth
                        .userInfoEndpoint(endPoint -> endPoint.userService(oauthUserService))
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                                userService.processOAuthPostLogin(oAuth2User.getEmail());
                            }
                        }))

                ;

        return http.build();
    }
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//
//        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
//
//        userService.processOAuthPostLogin(oauthUser.getEmail());
//
//        response.sendRedirect("/list");
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/", "/login", "/oauth/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
//                .userInfoEndpoint()
//                .userService(oauthUserService);
//    }
//
//    @Autowired
//    private CustomOAuth2UserService oauthUserService;


}
