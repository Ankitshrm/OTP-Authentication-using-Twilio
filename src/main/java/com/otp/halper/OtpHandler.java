package com.otp.halper;

import com.otp.model.RequestSms;
import com.otp.services.OTPService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OtpHandler {
    @Autowired
    private OTPService otpService;

    public Mono<ServerResponse> sendOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestSms.class)
                .flatMap(it -> otpService.sendOTP(it))
                .flatMap(it -> ServerResponse.status(HttpStatus.SC_OK)
                        .body(BodyInserters.fromValue(it)));
    }

    public Mono<ServerResponse> validateOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestSms.class)
                .flatMap(it -> otpService.validateOTP(it.getOtp(), it.getUsername()))
                .flatMap(it -> ServerResponse.status(HttpStatus.SC_OK)
                        .bodyValue(it));
    }

}