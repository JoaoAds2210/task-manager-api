package com.example.task_manager_api.dtos.user;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public record UserResponse(

        Long id,
        String name,
        String email,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
