package com.example.coinbe.repository;

import com.example.coinbe.entity.Squat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SquatRepository extends JpaRepository<Squat, UUID> {
}