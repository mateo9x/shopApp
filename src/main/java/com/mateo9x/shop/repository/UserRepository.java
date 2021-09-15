package com.mateo9x.shop.repository;

import java.util.Optional;

import com.mateo9x.shop.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByUsername(String username);
    Optional<User> findByMail(String mail);
}
