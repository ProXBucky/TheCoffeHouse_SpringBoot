package com.hungnv.TheCoffeeHouse.security;

import com.hungnv.TheCoffeeHouse.exception.UserNotFoundException;
import com.hungnv.TheCoffeeHouse.model.Users;
import com.hungnv.TheCoffeeHouse.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UserNotFoundException("User with username " + username + "not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Users user) {

        String role;
        if (Objects.equals(user.getRoleId(), "R2")) {
            role = "ROLE_STAFF";
        } else {
            role = "ROLE_ADMIN";
        }
        return List.of(new SimpleGrantedAuthority(role));
    }


}

