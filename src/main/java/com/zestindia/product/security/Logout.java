package com.zestindia.product.security;

import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class Logout {

    private HashSet<String> blackListToken = new HashSet<>();

    public void blackListToken(String token){
        blackListToken.add(token);
    }

    public boolean isTokenBlackList(String token){
        return blackListToken.contains(token);
    }
}
