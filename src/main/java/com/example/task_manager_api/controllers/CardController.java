package com.example.task_manager_api.controllers;

import com.example.task_manager_api.dtos.card.CardRequest;
import com.example.task_manager_api.dtos.card.CardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.task_manager_api.services.CardServices;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardServices cardServices;

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(cardServices.findById(id));
    }

    public ResponseEntity<List<CardResponse>> findAll(){
        return ResponseEntity.ok(cardServices.findAll());
    }

    public ResponseEntity<CardResponse> create(@PathVariable Long boardId, @Valid @RequestBody CardRequest cardRequest){
        CardResponse response = cardServices.create(boardId, cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> update(@PathVariable Long id, @Valid @RequestBody CardRequest cardRequest) {

        return ResponseEntity.ok(cardServices.update(id, cardRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cardServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
