package com.example.task_manager_api.services;

import com.example.task_manager_api.dtos.user.UserRequest;
import com.example.task_manager_api.dtos.user.UserResponse;
import com.example.task_manager_api.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.task_manager_api.mappers.UserMapper;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Service;
import com.example.task_manager_api.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SmartInitializingSingleton springDocApiVersionCustomizer;

    @Transactional
    public UserResponse criarUser(UserRequest requestDTO) {

        User user = userMapper.toEntity(requestDTO);
        User userSave = userRepository.save(user);

        return userMapper.toDTO(userSave);

    }

    @Transactional
    public List<UserResponse> listarTodos(){
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
