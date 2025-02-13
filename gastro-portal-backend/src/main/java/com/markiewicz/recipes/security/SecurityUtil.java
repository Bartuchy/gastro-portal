package com.markiewicz.recipes.security;

public class SecurityUtil {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    public static final String[] PERMITTED_ALL_PATHS = new String[] {
            "/api/v1/auth/**",
            "/api/v1/demo-controller/test",
            "/api/v1/auth/{email}/qr-code",
            "/user/{email}/using2fa"
    };

    public static final String[] USER_PATHS = new String[] {
            "/api/v1/demo-controller/user"
    };

    public static final String[] ADMIN_PATHS = new String[] {
            "/api/v1/demo-controller/admin"
    };
}
