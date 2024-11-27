package com.DEMO1.demo.service;

import com.DEMO1.demo.model.User;
import com.DEMO1.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long id) throws Exception {
        return userRepository.findById(id);
    }

    public User getUserByUserName(String username) throws Exception {
        return userRepository.findByUsername(username).orElseThrow(() -> new Exception("Username not found exception."));
    }

    public User updateUserProfile(User updatedUser) throws Exception {
        Optional<User> existingUser = userRepository.findById(updatedUser.getId());
        if (!existingUser.isPresent()) {
            throw new Exception("User not found.");
        }
        User user = existingUser.get();


        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        user.setNumber(updatedUser.getNumber());
        user.setGender(updatedUser.getGender());
        user.setAddress(updatedUser.getAddress());
        user.setDob(updatedUser.getDob());

        return userRepository.save(user);
    }
    @Transactional
    public void deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            System.out.println("Deleting user with ID: " + id);  // Add log to confirm deletion
            userRepository.delete(user.get());
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

}


