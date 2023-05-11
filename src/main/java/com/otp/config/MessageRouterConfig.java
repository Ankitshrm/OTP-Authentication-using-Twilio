package com.otp.config;

import com.otp.halper.OtpHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MessageRouterConfig {
    @Autowired
    private OtpHandler handler;
    @Bean
    public RouterFunction<ServerResponse> handleSms(){
        return RouterFunctions.route()
                .POST("/router/sendOTP",handler::sendOTP)
                    .POST("/router/validateOTP",handler::validateOTP)
                .build();
    }

}
