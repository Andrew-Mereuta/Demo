package com.example.moduleone.services;

import com.example.moduleone.models.Role;
import com.example.moduleone.models.UserEntity;
import com.example.moduleone.models.UserRequest;
import com.example.moduleone.reporsitories.RoleRepository;
import com.example.moduleone.reporsitories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> getAllUsers(){
        return (List<UserEntity>) userRepository.findAll();
    }

    public void saveUser(UserRequest user) {
        if(userRepository.findUserEntityByNetid(user.getNetid()) != null)
            throw new RuntimeException("User already exists!");

        Role student = roleRepository.findRoleByRole("STUDENT");
        userRepository.save(new UserEntity(user.getNetid(), passwordEncoder.encode(user.getPassword()), Set.of(student)));
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

        Role student = roleRepository.findRoleByRole("STUDENT");
        Role ta = roleRepository.findRoleByRole("TA");
        userRepository.save(new UserEntity(user.getNetid(), passwordEncoder.encode(user.getPassword()), Set.of(student, ta)));
    }
}
