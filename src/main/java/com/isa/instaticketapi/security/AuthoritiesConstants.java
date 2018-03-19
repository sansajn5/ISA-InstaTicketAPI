package com.isa.instaticketapi.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String SUPER_ADMIN = "SUPER_ADMIN";

    public static final String ADMIN = "ROLE_ADMIN";
    
    public static final String FANZONE_ADMIN = "ROLE_FANZONE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
