package com.example.expressfood.security;

public class JWTUtil {
    public static final String SECRET="BAZBOUZ";
    public static final String AUTH_HEADER="Authorization";
    public static final long EXPIRE_ACCESS_TOKEN=24*60*60*1000;
    public static final long EXPIRE_REFRESH_TOKEN =100*60*1000 ;
    public static final String PREFIX = "Bearer ";
}
