package com.otp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "phoneNo")
    private String phone;
    @Column(name ="username")
    private String username;
    @Column(name="otp")
    private String otp;
}
