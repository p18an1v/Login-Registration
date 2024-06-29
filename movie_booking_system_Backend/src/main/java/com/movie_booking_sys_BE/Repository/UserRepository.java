package com.movie_booking_sys_BE.Repository;

import com.movie_booking_sys_BE.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    boolean existsByUserNameOrEmail(String userName, String email);
}
