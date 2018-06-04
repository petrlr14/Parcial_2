package com.pdm00057616.gamenews.models;

public class Login {

    private boolean isOKResponse;
    private String token;

    public boolean isOKResponse() {
        return isOKResponse;
    }

    public void setOKResponse(boolean OKResponse) {
        isOKResponse = OKResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
