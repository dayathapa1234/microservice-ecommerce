package com.ecommerce.account.service.impl;

import com.ecommerce.account.entity.User;
import com.ecommerce.account.enums.USER_ROLE;
import com.ecommerce.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private static final String SELLER_PREFIX = "seller_";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.startsWith(SELLER_PREFIX)){

        }
        else{
            User user = userRepository.findByEmail(username);
            if (user != null){
                return buildUserDetails(user.getEmail(), user.getPassword(), user.getRole());
            }
        }
        throw  new UsernameNotFoundException("User not found with email: " + username);
    }

    private UserDetails buildUserDetails(String email, String password, USER_ROLE role) {
        if (role==null) role=USER_ROLE.ROLE_CUSTOMER;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        return new org.springframework.security.core.userdetails.User(email,password,authorities);

    }
}
