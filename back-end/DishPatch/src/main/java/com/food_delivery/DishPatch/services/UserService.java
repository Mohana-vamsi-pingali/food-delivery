package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.DTOs.UserDTO;
import com.food_delivery.DishPatch.DTOs.UserLoginDTO;
import com.food_delivery.DishPatch.DTOs.UserResponseDTO;
import com.food_delivery.DishPatch.Exceptions.DuplicateEmailException;
import com.food_delivery.DishPatch.Exceptions.InvalidPasswordException;
import com.food_delivery.DishPatch.Exceptions.UserNotFoundException;
import com.food_delivery.DishPatch.models.User;
import com.food_delivery.DishPatch.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.food_delivery.DishPatch.models.User.Role;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserDTO user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DuplicateEmailException("Email Already Exists: "+user.getEmail()+" . Please Log in");
        }
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        if(user.getRole()!=null){
            Role role = Role.valueOf(user.getRole().toUpperCase());
            newUser.setRole(role);
        }
        User savedUser = userRepository.save(newUser);
        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole().name());
    }

    public String loginUser(UserLoginDTO user){
        Optional<User> requestedUser = userRepository.findByEmail((user.getEmail()));
        if(requestedUser.isEmpty()){
            throw new UserNotFoundException("Email id Not found. Please Sign up.");
        }
        else {
            boolean isCorrect = passwordEncoder.matches(user.getPassword(), requestedUser.get().getPasswordHash());
            if(isCorrect){
                return requestedUser.get().getRole().name();
            }
            else {
                throw new InvalidPasswordException("Invalid Credentials");
            }
        }
    }
}
