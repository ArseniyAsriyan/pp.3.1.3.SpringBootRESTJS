package com.PP311.mySpringBootApp.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
//        httpServletResponse.sendRedirect("/hello");
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if(roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else if(roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user");
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }

//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response, Authentication authentication) throws IOException {
//
//        String targetUrl = authentication.getAuthorities().toString().contains("ROLE_ADMIN") ? "/admin" : "/user";
//        redirectStrategy.sendRedirect(request, response, targetUrl);
//    }
}