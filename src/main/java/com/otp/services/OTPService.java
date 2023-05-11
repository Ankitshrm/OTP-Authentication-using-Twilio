package com.otp.services;

import com.otp.config.MessageConfig;
import com.otp.dao.UserRepository;
import com.otp.model.OtpStatus;
import com.otp.model.RequestSms;
import com.otp.model.ResponseSms;
import com.otp.model.User;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OTPService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageConfig messageConfig;

    @Autowired
    private AuthDbService authDbService;

    Map<String,String> map = new HashMap<String,String>();

    public Mono<ResponseSms> sendOTP(RequestSms requestSms){
        ResponseSms responseSms =null;
        try{
        PhoneNumber to = new PhoneNumber(requestSms.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(messageConfig.getT_number());
        String otp =gernerateOTP();
        String otpMessage = "Dear Customer, Your OTP is "+ otp+". Use this otp to login.";

        //db start
        User user = new User();
        user.setUsername(requestSms.getUsername());
        user.setPhone(requestSms.getPhoneNumber());
        user.setOtp(otp);
        userRepository.save(user);
        //db ends

        Message message = Message.creator(to,from,otpMessage).create();
        map.put(requestSms.getUsername(),otp);
        responseSms=new ResponseSms(OtpStatus.DELIVERED,otpMessage);
        }catch (Exception e){
            responseSms=new ResponseSms(OtpStatus.FAILED,e.getMessage());
        }
        return Mono.just(responseSms);
    }

    public Mono<String> validateOTP(String userInputOtp,String username){
        if (userInputOtp.equals(map.get(username))){
            map.remove(username,userInputOtp);
            return Mono.just("Valid OTP! Welcome");
        }else{
            return Mono.error(new IllegalArgumentException("Invalid OTP!! Please retry later."));
        }
    }

    private String gernerateOTP(){
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
