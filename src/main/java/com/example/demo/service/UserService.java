package com.example.demo.service;

import com.example.demo.model.Developer;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Developer> findAll() {
        return userRepository.findAll();
    }

    public Developer findCurrent() {
        for (Developer user : userRepository.findAll()) {
            if (user.isCurrent()) {
                return user;
            }
        }
        return null;
    }

    public Developer findByName(String name, String password) {
        for (Developer user : userRepository.findAll()) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public Developer save(Developer project) {
        return userRepository.save(project);
    }
}