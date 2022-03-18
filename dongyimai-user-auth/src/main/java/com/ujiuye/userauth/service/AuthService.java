package com.ujiuye.userauth.service;

import com.ujiuye.userauth.util.TokenUtil;

public interface AuthService {
    TokenUtil login(String username, String password, String clientId, String clientSecret);
}
