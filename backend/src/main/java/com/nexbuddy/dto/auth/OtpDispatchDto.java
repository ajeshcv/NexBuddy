package com.nexbuddy.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpDispatchDto {
    private String email;
    private String deliveryMode;
    private String devOtp;

    public OtpDispatchDto() {
    }

    public OtpDispatchDto(String email, String deliveryMode, String devOtp) {
        this.email = email;
        this.deliveryMode = deliveryMode;
        this.devOtp = devOtp;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDeliveryMode() { return deliveryMode; }
    public void setDeliveryMode(String deliveryMode) { this.deliveryMode = deliveryMode; }
    public String getDevOtp() { return devOtp; }
    public void setDevOtp(String devOtp) { this.devOtp = devOtp; }
}
