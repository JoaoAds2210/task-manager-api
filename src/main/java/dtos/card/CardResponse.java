package dtos.card;

import enums.Priority;
import enums.Status;

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
