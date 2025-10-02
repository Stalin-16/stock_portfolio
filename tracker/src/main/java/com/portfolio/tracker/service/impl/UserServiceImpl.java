package com.portfolio.tracker.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.tracker.dto.request.UserRequestDto;
import com.portfolio.tracker.dto.response.UserResponse;
import com.portfolio.tracker.entity.User;
import com.portfolio.tracker.repository.UserRepository;
import com.portfolio.tracker.service.IUserService;
import com.portfolio.tracker.util.JwtUtil;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.encoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    @Override
    public UserResponse createUser(UserRequestDto userRequestDto) {

        if (userRepository.findByUsername(userRequestDto.getUsername()) == null) {
            User user = new User();
            String encodedPassword = encoder.encode(userRequestDto.getPassword());
            user.setUsername(userRequestDto.getUsername());
            user.setPassword(encodedPassword);
            user.setEmail(userRequestDto.getEmail());
            userRepository.save(user);

            String token = jwtUtil.generateToken(user.getUsername());

            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setToken(token);
            return userResponse;
        }

        throw new RuntimeException("User already exists");
    }

    @Override
    public UserResponse signin(UserRequestDto userRequestDto) {

        User user = userRepository.findByUsername(userRequestDto.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!encoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setToken(token);
        return userResponse;
    }
}
