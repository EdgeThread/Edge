package com.ujiuye.userauth.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUtil implements Serializable {
    String accessToken;
    String refreshToken;
    String jti;
}
