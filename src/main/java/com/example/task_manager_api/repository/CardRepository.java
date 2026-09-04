package com.example.task_manager_api.repository;

import com.example.task_manager_api.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
