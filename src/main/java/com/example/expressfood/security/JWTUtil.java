package com.example.expressfood.security;

public final class JWTUtil {
    public static final String SECRET=";-`LKszS.kH|9$5,cH#'I,yingPqop";
    public static final String AUTH_HEADER="Authorization";
    public static final Integer EXPIRE_ACCESS_TOKEN=24*60*60*1000;
    public static final String PREFIX = "Bearer ";

    private JWTUtil(){

    }
}
