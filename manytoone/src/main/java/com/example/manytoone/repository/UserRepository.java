package com.example.manytoone.repository;

import com.example.manytoone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
