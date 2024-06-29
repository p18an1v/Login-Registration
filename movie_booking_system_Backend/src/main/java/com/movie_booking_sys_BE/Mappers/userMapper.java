package com.movie_booking_sys_BE.Mappers;

import com.movie_booking_sys_BE.DTO.userDTO;
import com.movie_booking_sys_BE.Entity.User;

public class userMapper {

        //it will map user jpa entity to userDto - take user data and convert into userdto data
      // map user to user dto
        public static userDTO mapUserToUserDTO(User user){
            return new userDTO(
                    user.getUserId(),
                    user.getUserName(),
                    user.getMobile(),
                    user.getEmail(),
                    user.getPassword(),
                    user.isOtpVerified(),
                     user.getRole()
            );
        }
        //it will map userDTO to user jpa entity - take userDto and convert into user entity
     // map userDto to user
        public static User mapUserDtoToUser(userDTO userDTO){
            return new User(
                   userDTO.getUserId(),
                    userDTO.getUserName(),
                    userDTO.getMobile(),
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    userDTO.isOtpVerified(),
                    userDTO.getRole()
            );
        }
}
