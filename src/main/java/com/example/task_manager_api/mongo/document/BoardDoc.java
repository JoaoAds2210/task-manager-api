package com.example.task_manager_api.mongo.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document (collection = "boards")
public class BoardDoc {
    
    @Id
    private String id;

    private String title;
    private String description;
    private String userName;
    private Boolean archived;
    private LocalDateTime createdAt;
    
}
