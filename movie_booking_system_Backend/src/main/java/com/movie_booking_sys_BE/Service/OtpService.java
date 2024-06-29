package com.movie_booking_sys_BE.Service;

import com.movie_booking_sys_BE.Entity.OtpData;
import com.movie_booking_sys_BE.Entity.User;
import com.movie_booking_sys_BE.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    private final Map<String, User> temporaryUsers = new HashMap<>();
    private final Map<String, Integer> otpStorage = new HashMap<>();

    public void storeTemporaryUser(User user) {
        temporaryUsers.put(user.getEmail(), user);
    }

    public User getTemporaryUser(String email) {
        return temporaryUsers.get(email);
    }

    public void removeTemporaryUser(String email) {
        temporaryUsers.remove(email);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendOtp(OtpData otp) {
        int generatedOtp = generateOtp();
        otpStorage.put(otp.getEmail(), generatedOtp);
        // Logic to send OTP (e.g., via email or SMS)
        sendEmail(otp.getEmail(), "Your OTP Code", "Your OTP code is: " + generatedOtp);

        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP sent successfully");
        System.out.println("message "+ " OTP sent successfully");
    }

    public Map<String, String> verifyOtp(OtpData otp) {
        Map<String, String> result = new HashMap<>();
        Integer storedOtp = otpStorage.get(otp.getEmail());
        if (storedOtp != null && storedOtp.equals(otp.getOtp())) {
            result.put("message", "OTP verified successfully");
            otpStorage.remove(otp.getEmail());
        } else {
            result.put("message", "Invalid OTP");
        }
        return result;
    }



    private int generateOtp() {
        // Generate a random 6-digit OTP
        return (int) (Math.random() * 900000) + 100000;
    }

}
