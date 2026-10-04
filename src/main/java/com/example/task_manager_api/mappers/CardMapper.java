package com.example.task_manager_api.mappers;

import com.example.task_manager_api.dtos.card.CardRequest;
import com.example.task_manager_api.dtos.card.CardResponse;
import com.example.task_manager_api.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public static Card toEntity(CardRequest dto, Long boardId){
        if(dto == null){
            return null;
        }

        return Card.builder()
                .boardId(boardId)
                .title(dto.title())
                .description(dto.description())
                .status(dto.status())
                .priority(dto.priority())
                .position(dto.position())
                .assigneeName(dto.assigneeName())
                .dueDate(dto.dueDate())
                .build();
    }

    public static CardResponse toResponse(Card card){
        if(card == null){
            return null;
        }

        return new CardResponse(
                card.getId(),
                card.getBoardId(),
                card.getTitle(),
                card.getDescription(),
                card.getStatus(),
                card.getPriority(),
                card.getPosition(),
                card.getAssigneeName(),
                card.getDueDate(),
                card.getCompletedAt(),
                card.getCreatedAt(),
                card.getUpdatedAt()
        );

    }
}
