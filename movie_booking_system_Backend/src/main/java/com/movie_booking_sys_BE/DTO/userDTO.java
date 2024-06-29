package com.movie_booking_sys_BE.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class userDTO {
    private Long userId;
    private String userName;
    private String mobile;
    private String email;
    private String password;
    private boolean isOtpVerified = false;
    private String role;
}
