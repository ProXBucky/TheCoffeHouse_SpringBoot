package com.hungnv.TheCoffeeHouse.service;

import com.hungnv.TheCoffeeHouse.dto.AuthDTO;
import com.hungnv.TheCoffeeHouse.exception.UserExistException;
import com.hungnv.TheCoffeeHouse.exception.UserNotFoundException;
import com.hungnv.TheCoffeeHouse.exception.common.BadRequestException;
import com.hungnv.TheCoffeeHouse.model.Users;
import com.hungnv.TheCoffeeHouse.repository.UsersRepository;
import com.hungnv.TheCoffeeHouse.security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    public AuthDTO.RegisterResponse register(AuthDTO.RegisterRequest registerRequest) {
        Users checkExist = userRepository.findByEmail(registerRequest.getEmail());
        if (checkExist != null) {
            throw new UserExistException("User is exist");
        }
        Users user = Users.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .address(registerRequest.getAddress())
                .roleId("R2")
                .phone(registerRequest.getPhone())
                .isApproved(0)
                .build();
        Users userSaved = userRepository.save(user);
        AuthDTO.RegisterResponse userReponse = new AuthDTO.RegisterResponse(
                userSaved.getId(),
                userSaved.getEmail(),
                userSaved.getFirstName(),
                userSaved.getLastName(),
                userSaved.getAddress(),
                userSaved.getRoleId(),
                userSaved.getPhone(),
                userSaved.getIsApproved()
        );
        return userReponse;
    }

    public AuthDTO.AuthResponse login(AuthDTO.AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            Users user = userRepository.findByEmail(authentication.getName());
            if (user == null) {
                System.out.println("User not found");
                throw new UserNotFoundException("User not found");
            }
            if (user.getIsApproved() == 0) {
                System.out.println("User is not active");
                throw new BadRequestException("User is not active");
            }

            String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRoleId());

            return new AuthDTO.AuthResponse(authRequest.getEmail(), token);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid credentials");
            throw e;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            throw e;
        }
    }



}