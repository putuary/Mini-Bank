package com.miniBank.service.impl;

import com.miniBank.model.entity.AppUser;
import com.miniBank.model.entity.User;
import com.miniBank.repository.UserRepository;
import com.miniBank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;

    @Override
    public AppUser loadUserByUserId(String id) throws UsernameNotFoundException { // method ini untuk memverifikasi token JWT
        User user = userRepository.findId(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return AppUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return AppUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
