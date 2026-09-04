package com.example.task_manager_api.dtos.card;

import com.example.task_manager_api.enums.Priority;
import com.example.task_manager_api.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CardRequest (
        @NotBlank(message = "O título é obrigatório")
        String title,

        String description,

        @NotNull(message = "O status é obrigatório")
        Status status,

        @NotNull(message = "A prioridade é obrigatória")
        Priority priority,

        Integer position,

        String assigneeName,

        LocalDateTime dueDate
){}
