package com.security.module.user.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private final String verificationCode;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        verificationCode = request.getParameter("code");
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
