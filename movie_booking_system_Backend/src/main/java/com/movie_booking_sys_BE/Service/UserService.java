package com.movie_booking_sys_BE.Service;

import com.movie_booking_sys_BE.Controller.UserController;
import com.movie_booking_sys_BE.Entity.User;
import com.movie_booking_sys_BE.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        String emailDomain = user.getEmail().split("@")[1];
        if ("securityboat.in".equals(emailDomain)) {
            user.setRole("ROLE_ADMIN");
        } else {
            user.setRole("ROLE_USER");
        }
        user.setOtpVerified(true);
        userRepository.save(user);
        logger.info("User saved in the database");
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean userExists(String userName, String email) {
        return userRepository.existsByUserNameOrEmail(userName, email);
    }

    public boolean validateCredentials(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
