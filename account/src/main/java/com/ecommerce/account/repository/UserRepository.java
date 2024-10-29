package com.ecommerce.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.account.entity.User;
public interface UserRepository extends JpaRepository< User, Long> {
    User findByEmail(String email);
}
