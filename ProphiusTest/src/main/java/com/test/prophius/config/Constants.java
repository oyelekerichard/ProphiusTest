package com.test.prophius.config;

public class Constants {

    public static final String SIGNING_KEY = "pro123r";
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 24 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";

    public static final String USER_ROLE_ADD_ACTION = "add-role";
    public static final String USER_ROLE_REMOVE_ACTION = "remove-role";

    public static final Long ADMIN_ROLE = 4L;
    public static final Long USER_ROLE = 5L;
    public static final Long ROLE_MAKER = 10L;
}