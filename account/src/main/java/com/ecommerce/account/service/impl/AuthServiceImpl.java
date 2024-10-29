package com.ecommerce.account.service.impl;

import com.ecommerce.account.config.JwtProvider;
import com.ecommerce.account.entity.User;
import com.ecommerce.account.enums.USER_ROLE;
import com.ecommerce.account.repository.UserRepository;
import com.ecommerce.account.response.SignupRequest;
import com.ecommerce.account.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    @Override
    public String createUSer(SignupRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null){
            User newUser = new User();
            newUser.setEmail(request.getEmail());
            String[] nameParts = request.getFullName().trim().split(" ");
            if (nameParts.length > 0) {
                newUser.setFirstName(nameParts[0]);
                if (nameParts.length > 1) {
                    newUser.setLastName(nameParts[nameParts.length - 1]);
                } else {
                    newUser.setLastName("");
                }
            }
            newUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            newUser.setMobileNumber("07586948503");
            newUser.setPassword(passwordEncoder.encode(request.getOtp()));

            user = userRepository.save(newUser);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }
}
