package com.example.task_manager_api.repository;

import com.example.task_manager_api.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
