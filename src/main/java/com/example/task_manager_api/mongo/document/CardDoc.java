package com.example.task_manager_api.mongo.document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Document(collection = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CardDoc{

    @Id
    private String id;

    private String boardId;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String assigneeName;

    private Integer position;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

}