package com.example.demo.repository;

import com.example.demo.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Developer, Long> {
}
