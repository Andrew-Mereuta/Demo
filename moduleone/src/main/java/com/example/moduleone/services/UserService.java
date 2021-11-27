package com.example.moduleone.services;

import com.example.moduleone.models.Course;
import com.example.moduleone.models.UserEntity;
import com.example.moduleone.models.UserRequest;
import com.example.moduleone.reporsitories.CourseRepository;
import com.example.moduleone.reporsitories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       CourseRepository courseRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> getAllUsers(){
        return (List<UserEntity>) userRepository.findAll();
    }

    public void saveUser(UserRequest user) {
        if(userRepository.findUserEntityByNetid(user.getNetid()) != null)
            throw new RuntimeException("User already exists!");

        Optional<Course> oop = courseRepository.findById("OOP");
        userRepository.save(new UserEntity(user.getNetid(), passwordEncoder.encode(user.getPassword()), Set.of(oop.orElseThrow()), new HashSet<>()));
    }

    @Override
    public UserDetails loadUserByUsername(String netid) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntityByNetid(netid);
        return user;
    }

    public UserEntity findUserByNetid(String netid) throws UsernameNotFoundException {
        return userRepository.findUserEntityByNetid(netid);
    }

    public void saveUserAndTA(UserRequest user) {
        if(userRepository.findUserEntityByNetid(user.getNetid()) != null)
            throw new RuntimeException("User already exists!");

        Optional<Course> oopp = courseRepository.findById("OOPP");
        Optional<Course> oop = courseRepository.findById("OOP");
        userRepository.save(new UserEntity(user.getNetid(), passwordEncoder.encode(user.getPassword()), Set.of(oopp.orElseThrow()), Set.of(oop.orElseThrow())));
    }
}
