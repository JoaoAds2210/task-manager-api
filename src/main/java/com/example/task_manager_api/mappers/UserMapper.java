package com.example.task_manager_api.mappers;

import com.example.task_manager_api.dtos.user.UserRequest;
import com.example.task_manager_api.dtos.user.UserResponse;
import com.example.task_manager_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest requestDTO) {
        User user = new User();
        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email());
        user.setSenha(requestDTO.senha()); // TODO - Depois encriptar com Bcrypt, quando o Security estiver pronto

        return user;
    }

    public UserResponse toDTO(User user) {
        return new UserResponse(

                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()

        );
    }
}
