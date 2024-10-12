package com.hungnv.TheCoffeeHouse.service;
import com.hungnv.TheCoffeeHouse.dto.AuthDTO;
import com.hungnv.TheCoffeeHouse.dto.CommonDTO;
import com.hungnv.TheCoffeeHouse.dto.UserDTO;
import com.hungnv.TheCoffeeHouse.exception.UserExistException;
import com.hungnv.TheCoffeeHouse.exception.UserNotFoundException;
import com.hungnv.TheCoffeeHouse.model.Users;
import com.hungnv.TheCoffeeHouse.repository.OrderRepository;
import com.hungnv.TheCoffeeHouse.repository.ProductRepository;
import com.hungnv.TheCoffeeHouse.repository.StoresRepository;
import com.hungnv.TheCoffeeHouse.repository.UsersRepository;
import com.hungnv.TheCoffeeHouse.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final StoresRepository storesRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthDTO.RegisterResponse add(AuthDTO.RegisterRequest registerRequest) {
        Users checkExist = usersRepository.findByEmail(registerRequest.getEmail());
        if (checkExist != null) {
            throw new UserExistException("User is exist");
        }

        Users user = Users.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .address(registerRequest.getAddress())
                .roleId("R1")
                .phone(registerRequest.getPhone())
                .isApproved(1)
                .build();
        Users userSaved = usersRepository.save(user);
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

    public UserDTO.UserResponse approve(Long adminId) {
        Users user = usersRepository.findById(adminId).orElseThrow(() ->
                new UserNotFoundException("User not found with ID: " + adminId)
        );
        user.setIsApproved(1);
        usersRepository.save(user);
        UserDTO.UserResponse userReponse = new UserDTO.UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getRoleId(),
                user.getPhone(),
                user.getIsApproved()
        );
        return userReponse;
    }

    public void delete(Long adminId) {
        Users user = usersRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + adminId));
        usersRepository.delete(user);
    }

    public UserDTO.UserResponse update(UserDTO.UserUpdate data) {
        System.out.println(data);
        Users user = usersRepository.findById(data.getId()).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + data.getId()));
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setFirstName(data.getFirstName());
        user.setLastName(data.getLastName());
        user.setAddress(data.getAddress());
        user.setPhone(data.getPhone());
        usersRepository.save(user);
        UserDTO.UserResponse userResponse = UserDTO.UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roleId(user.getRoleId())
                .address(user.getAddress())
                .phone(user.getPhone())
                .isApproved(user.getIsApproved())
                .build();
        return userResponse;
    }

    @Transactional(readOnly = true)
    public UserDTO.UserResponse getById(int adminId) {
        Users user = usersRepository.findById(Long.valueOf(adminId)).orElseThrow(() -> new UserNotFoundException("User is not found with ID: " + adminId));
        if(user.getIsApproved() == null || user.getIsApproved() == 0){
            throw new UserNotFoundException("User is not active");
        }
        UserDTO.UserResponse userResponse = UserDTO.UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roleId(user.getRoleId())
                .address(user.getAddress())
                .phone(user.getPhone())
                .isApproved(user.getIsApproved())
                .build();
        return userResponse;
    }

    public List<UserDTO.UserResponseWithRole> getAllNotApproved() {
        List<Users> allAdmin = usersRepository.findAllApprovedAdmins(0);
        return allAdmin.stream().map(user -> {
            UserDTO.UserResponseWithRole dto = UserDTO.UserResponseWithRole.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .address(user.getAddress())
                    .phone(user.getPhone())
                    .isApproved(user.getIsApproved())
                    .roleValueEn(user.getRoleData().getValueEn())
                    .roleValueVn(user.getRoleData().getValueVn())
                    .build();
            return dto;
        }).collect(Collectors.toList());
    }

    public List<UserDTO.UserResponseWithRole> getAll() {
        List<Users> allAdmin = usersRepository.findAllApprovedAdmins(1);
        return allAdmin.stream().map(user -> {
            UserDTO.UserResponseWithRole dto = UserDTO.UserResponseWithRole.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .address(user.getAddress())
                    .phone(user.getPhone())
                    .isApproved(user.getIsApproved())
                    .roleValueEn(user.getRoleData().getValueEn())
                    .roleValueVn(user.getRoleData().getValueVn())
                    .build();
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Users test(int adminId) {
        System.out.println("service test");
        Users user = usersRepository.findById(Long.valueOf(adminId)).orElseThrow(() -> new UserNotFoundException("User is not found with ID: " + adminId));
        System.out.println(user);
        return user;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String author(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        String role = "";
        if (token != null && jwtTokenProvider.validateToken(token)) {
            role = jwtTokenProvider.getRoleFromToken(token);
        }
        return role;
    }
}
