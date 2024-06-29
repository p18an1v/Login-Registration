package com.movie_booking_sys_BE.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_Id")
    private Long userId;
    @Column(name="user_Name")
    private String userName;
    @Column(name="user_Mobile")
    private String mobile;
    @Column(name="user_Email", nullable = false,unique = true)
    private String email;
    @Column(name="user_password")
    private String password;
    @Column(name="user_otp_verified")
    private boolean isOtpVerified = false;
    @Column(name="user_role")
    private String role;
}
