package com.mysocial.flipr.models;

public class OTPModel {
    public String token,otp;

    public OTPModel(String token, String otp) {
        this.otp = otp;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
