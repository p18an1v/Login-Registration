package com.movie_booking_sys_BE.Controller;

import com.movie_booking_sys_BE.Entity.OtpData;
import com.movie_booking_sys_BE.Entity.User;
import com.movie_booking_sys_BE.Service.OtpService;
import com.movie_booking_sys_BE.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3030")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^(\\+91|91)?[7-9]\\d{9}$");

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        logger.info("Register request received for user: " + user.getEmail());

        Map<String, String> response = new HashMap<>();

        if (user.getUserName().isEmpty() || !user.getUserName().matches("[a-zA-Z ]+") && user.getUserName().length() <= 3) {
            response.put("message", "Invalid full name or Username must be longer than 3 characters.");
            return ResponseEntity.badRequest().body(response);
        }
        if (!MOBILE_PATTERN.matcher(user.getMobile()).matches()) {
            response.put("message", "Invalid mobile number.");
            return ResponseEntity.badRequest().body(response);
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            response.put("message", "Invalid email address.");
            return ResponseEntity.badRequest().body(response);
        }
        if (user.getPassword().length() < 8) {
            response.put("message", "Password must be at least 8 characters long.");
            return ResponseEntity.badRequest().body(response);
        }
        if (userService.userExists(user.getUserName(), user.getEmail())) {
            response.put("message", "User with this username or email already exists.");
            return ResponseEntity.badRequest().body(response);
        }

        otpService.storeTemporaryUser(user);
        OtpData otp = new OtpData(user.getEmail(), 0);
        otpService.sendOtp(otp);

        response.put("message", "User OTP sent for verification successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-registration-otp")
    public ResponseEntity<Map<String, String>> verifyRegistrationOtp(@RequestBody OtpData otp) {
        Map<String, String> response = new HashMap<>();
        Map<String, String> verificationResult = otpService.verifyOtp(otp);
        String message = verificationResult.get("message");

        if ("OTP verified successfully".equals(message)) {
            User user = otpService.getTemporaryUser(otp.getEmail());
            if (user != null) {
                user.setOtpVerified(true);
                userService.saveUser(user);
                otpService.removeTemporaryUser(otp.getEmail());
                response.put("message", "User registration completed successfully.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "No registration data found for this OTP.");
                return ResponseEntity.badRequest().body(response);
            }
        } else {
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        if (userService.validateCredentials(user.getEmail(), user.getPassword())) {
            User userRole = userService.findByEmail(user.getEmail());
            String role = userRole.getRole();

            String dashboardUrl = "/user-dashboard";
            if ("ROLE_ADMIN".equals(role) && user.getEmail().endsWith("@admin.in")) {
                dashboardUrl = "/admin-dashboard";
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("role", role);
            response.put("dashboardUrl", dashboardUrl);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        }
    }

}
