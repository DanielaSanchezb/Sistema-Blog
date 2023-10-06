package com.sistema.blog.security;

public class JWTAuthResonseDTO {

    private String accessToken;

    private String tokenType = "Bearer";

    public JWTAuthResonseDTO() {
    }

    public JWTAuthResonseDTO(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public JWTAuthResonseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
