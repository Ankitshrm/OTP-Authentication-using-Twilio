package com.otp.model;

import lombok.Data;

@Data
public class RequestSms {

    private String phoneNumber;
    private String username;
    private String otp;
}
