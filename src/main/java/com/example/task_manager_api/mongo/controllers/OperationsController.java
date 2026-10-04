package com.example.task_manager_api.mongo.controllers;

import com.example.task_manager_api.mongo.document.BoardDoc;
import com.example.task_manager_api.mongo.document.CardDoc;
import com.example.task_manager_api.mongo.service.OperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mongo")
@RequiredArgsConstructor
public class OperationsController {

    private final OperationsService service;

    @PostMapping("/seed")
    public ResponseEntity<Map<String, Object>> seed() {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.seed());
    }

    @PostMapping("/boards")
    public ResponseEntity<BoardDoc> insertOneBoard(@RequestBody BoardDoc board) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insertOneBoard(board));
    }

    @PostMapping("/boards/bulk")
    public ResponseEntity<List<BoardDoc>> insertManyBoards(@RequestBody List<BoardDoc> boards) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insertManyBoards(boards));
    }

    @PostMapping("/cards/bulk")
    public ResponseEntity<List<CardDoc>> insertManyCards(@RequestBody List<CardDoc> cards) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insertManyCards(cards));
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardDoc>> findAllBoards() {
        return ResponseEntity.ok(service.findAllBoards());
    }

    @GetMapping("/cards")
    public ResponseEntity<List<CardDoc>> findAllCards() {
        return ResponseEntity.ok(service.findAllCards());
    }

    @GetMapping("/boards/user/{userName}")
    public ResponseEntity<List<BoardDoc>> boardsByUser(@PathVariable String userName) {
        return ResponseEntity.ok(service.findBoardsByUser(userName));
    }

    @GetMapping("/cards/board/{boardId}")
    public ResponseEntity<List<CardDoc>> cardsByBoard(@PathVariable String boardId) {
        return ResponseEntity.ok(service.findCardsByBoard(boardId));
    }

    @GetMapping("/cards/status/{status}")
    public ResponseEntity<List<CardDoc>> cardsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findCardsByStatus(status));
    }

    @PutMapping("/cards/{id}/status/{status}")
    public ResponseEntity<Map<String, Object>> updateOneCardStatus(@PathVariable String id, @PathVariable String status) {
        return ResponseEntity.ok(service.updateOneCardStatus(id, status));
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Map<String, Object>> deleteOneCard(@PathVariable String id) {
        return ResponseEntity.ok(service.deleteOneCard(id));
    }

    @GetMapping("/boards/archived/{value}")
    public ResponseEntity<List<BoardDoc>> boardsArchivedEq(@PathVariable boolean value) {
        return ResponseEntity.ok(service.boardsArchivedEq(value));
    }

    @GetMapping("/boards/title-not/{title}")
    public ResponseEntity<List<BoardDoc>> boardsTitleNe(@PathVariable String title) {
        return ResponseEntity.ok(service.boardsTitleNe(title));
    }

    @GetMapping("/cards/position/gt/{n}")
    public ResponseEntity<List<CardDoc>> positionGt(@PathVariable int n) {
        return ResponseEntity.ok(service.cardsPositionGt(n));
    }

    @GetMapping("/cards/position/gte/{n}")
    public ResponseEntity<List<CardDoc>> positionGte(@PathVariable int n) {
        return ResponseEntity.ok(service.cardsPositionGte(n));
    }

    @GetMapping("/cards/position/lt/{n}")
    public ResponseEntity<List<CardDoc>> positionLt(@PathVariable int n) {
        return ResponseEntity.ok(service.cardsPositionLt(n));
    }

    @GetMapping("/cards/position/lte/{n}")
    public ResponseEntity<List<CardDoc>> positionLte(@PathVariable int n) {
        return ResponseEntity.ok(service.cardsPositionLte(n));
    }

    @GetMapping("/cards/position/between")
    public ResponseEntity<List<CardDoc>> positionBetween(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(service.cardsPositionBetween(min, max));
    }

    @GetMapping("/cards/assignee-in")
    public ResponseEntity<List<CardDoc>> assigneeIn(@RequestParam List<String> names) {
        return ResponseEntity.ok(service.cardsAssigneeIn(names));
    }

    @GetMapping("/boards/user-nin")
    public ResponseEntity<List<BoardDoc>> userNin(@RequestParam List<String> names) {
        return ResponseEntity.ok(service.boardsUserNin(names));
    }

    @GetMapping("/cards/or")
    public ResponseEntity<List<CardDoc>> cardsOr(@RequestParam String status, @RequestParam String priority) {
        return ResponseEntity.ok(service.cardsOr(status, priority));
    }

    @GetMapping("/cards/and")
    public ResponseEntity<List<CardDoc>> cardsAnd(@RequestParam String status, @RequestParam String priority) {
        return ResponseEntity.ok(service.cardsAnd(status, priority));
    }

    @GetMapping("/boards/without-description")
    public ResponseEntity<List<BoardDoc>> boardsWithoutDescription() {
        return ResponseEntity.ok(service.boardsWithoutDescription());
    }

    @GetMapping("/cards/completed")
    public ResponseEntity<List<CardDoc>> cardsCompleted() {
        return ResponseEntity.ok(service.cardsCompleted());
    }

    @GetMapping("/cards/unassigned")
    public ResponseEntity<List<CardDoc>> cardsUnassigned() {
        return ResponseEntity.ok(service.cardsUnassigned());
    }
}