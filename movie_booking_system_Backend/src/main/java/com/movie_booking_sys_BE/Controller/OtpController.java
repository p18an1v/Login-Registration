package com.movie_booking_sys_BE.Controller;

import com.movie_booking_sys_BE.Entity.OtpData;
import com.movie_booking_sys_BE.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3030")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpData otp) {
        otpService.sendOtp(otp);
        return ResponseEntity.ok("OTP sent successfully.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpData otp) {
        Map<String, String> verificationResult = otpService.verifyOtp(otp);
        String message = verificationResult.get("message");
        if ("OTP verified successfully".equals(message)) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }
}

