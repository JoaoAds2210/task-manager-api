package com.example.task_manager_api.dtos.card;

import com.example.task_manager_api.enums.Priority;
import com.example.task_manager_api.enums.Status;

import java.time.LocalDateTime;

public record CardResponse(
        Long id,
        Long boardId,
        String title,
        String description,
        Status status,
        Priority priority,
        Integer position,
        String assigneeName,
        LocalDateTime dueDate,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
