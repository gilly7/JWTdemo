package com.mycompany.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.jwtdemo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
public UserEntity findByUsername(String username);
}
