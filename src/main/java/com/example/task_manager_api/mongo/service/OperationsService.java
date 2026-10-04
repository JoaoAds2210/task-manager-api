package com.example.task_manager_api.mongo.service;

import com.example.task_manager_api.mongo.document.BoardDoc;
import com.example.task_manager_api.mongo.document.CardDoc;
import com.example.task_manager_api.mongo.repository.MgBoardRepository;
import com.example.task_manager_api.mongo.repository.MgCardRepository;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OperationsService {

    private final MongoTemplate mongoTemplate;
    private final MgBoardRepository boardRepository;
    private final MgCardRepository cardRepository;

    private List<BoardDoc> boards(Criteria criteria) {
        return mongoTemplate.find(new Query(criteria), BoardDoc.class);
    }

    private List<CardDoc> cards(Criteria criteria) {
        return mongoTemplate.find(new Query(criteria), CardDoc.class);
    }

    //-----
    
    public Map<String, Object> seed() {
        cardRepository.deleteAll();
        boardRepository.deleteAll();

        List<BoardDoc> boards = boardRepository.insert(List.of(
                board("Sprint Backend", "Tarefas da API", "Ana", false, LocalDateTime.of(2026, 8, 1, 10, 0)),
                board("Site Institucional", "Layout e conteúdo", "Bruno", false, LocalDateTime.of(2026, 8, 10, 9, 0)),
                board("Projeto Antigo", null, "Ana", true, LocalDateTime.of(2026, 5, 20, 9, 0))
        ));
        String b1 = boards.get(0).getId();
        String b2 = boards.get(1).getId();
        String b3 = boards.get(2).getId();

        List<CardDoc> cards = cardRepository.insert(List.of(
                card(b1, "Criar entidade User", "DONE", "HIGH", 1, "Ana", LocalDateTime.of(2026, 8, 5, 0, 0), LocalDateTime.of(2026, 8, 4, 0, 0)),
                card(b1, "Criar CardController", "IN_PROGRESS", "HIGH", 2, "Bruno", LocalDateTime.of(2026, 8, 20, 0, 0), null),
                card(b1, "Implementar BCrypt", "TODO", "MEDIUM", 3, "Carla", LocalDateTime.of(2026, 9, 1, 0, 0), null),
                card(b1, "Documentar API", "TODO", "LOW", 4, null, LocalDateTime.of(2026, 9, 15, 0, 0), null),
                card(b2, "Desenhar layout", "DONE", "MEDIUM", 1, "Bruno", LocalDateTime.of(2026, 8, 14, 0, 0), LocalDateTime.of(2026, 8, 12, 0, 0)),
                card(b2, "Configurar hospedagem", "IN_PROGRESS", "LOW", 2, "Carla", LocalDateTime.of(2026, 8, 30, 0, 0), null),
                card(b2, "Escrever textos", "TODO", "MEDIUM", 3, "Ana", LocalDateTime.of(2026, 9, 10, 0, 0), null),
                card(b3, "Arquivar documentos", "DONE", "LOW", 1, "Ana", LocalDateTime.of(2026, 6, 1, 0, 0), LocalDateTime.of(2026, 6, 1, 0, 0))
        ));

        return Map.of("boardsInseridos", boards.size(), "cardsInseridos", cards.size());
    }

    //-------

    private BoardDoc board(String title, String description, String user, boolean archived, LocalDateTime createdAt) {
        return BoardDoc.builder().title(title).description(description).userName(user)
                .archived(archived).createdAt(createdAt).build();
    }

    private CardDoc card(String boardId, String title, String status, String priority, int position,
                         String assignee, LocalDateTime dueDate, LocalDateTime completedAt) {
        return CardDoc.builder().boardId(boardId).title(title).status(status).priority(priority)
                .position(position).assigneeName(assignee).dueDate(dueDate).completedAt(completedAt)
                .createdAt(LocalDateTime.now()).build();
    }

    public BoardDoc insertOneBoard(BoardDoc board) {
        board.setId(null);
        board.setCreatedAt(LocalDateTime.now());
        if (board.getArchived() == null) {
            board.setArchived(false);
        }
        return boardRepository.insert(board);
    }

    public List<BoardDoc> insertManyBoards(List<BoardDoc> boards) {
        boards.forEach(b -> {
            b.setId(null);
            b.setCreatedAt(LocalDateTime.now());
            if (b.getArchived() == null) {
                b.setArchived(false);
            }
        });
        return boardRepository.insert(boards);
    }

    public List<CardDoc> insertManyCards(List<CardDoc> cards) {
        cards.forEach(c -> {
            c.setId(null);
            c.setCreatedAt(LocalDateTime.now());
        });
        return cardRepository.insert(cards);
    }

    public List<BoardDoc> findAllBoards() {
        return boardRepository.findAll();
    }

    public List<CardDoc> findAllCards() {
        return cardRepository.findAll();
    }

    public List<BoardDoc> findBoardsByUser(String userName) {
        return boards(Criteria.where("userName").is(userName));
    }

    public List<CardDoc> findCardsByBoard(String boardId) {
        return cards(Criteria.where("boardId").is(boardId));
    }

    public List<CardDoc> findCardsByStatus(String status) {
        return cards(Criteria.where("status").is(status.toUpperCase()));
    }

    public Map<String, Object> updateOneCardStatus(String id, String status) {
        String newStatus = status.toUpperCase();
        Update update = new Update()
                .set("status", newStatus)
                .set("updatedAt", LocalDateTime.now());

        if ("DONE".equals(newStatus)) {
            update.set("completedAt", LocalDateTime.now());
        } else {
            update.unset("completedAt");
        }

        UpdateResult result = mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(id)), update, CardDoc.class);

        return Map.of("matchedCount", result.getMatchedCount(),
                "modifiedCount", result.getModifiedCount());
    }

    public Map<String, Object> deleteOneCard(String id) {
        DeleteResult result = mongoTemplate.remove(
                new Query(Criteria.where("id").is(id)), CardDoc.class);

        return Map.of("deletedCount", result.getDeletedCount());
    }

    public List<BoardDoc> boardsArchivedEq(boolean archived) {
        Query query = new BasicQuery(new Document("archived", new Document("$eq", archived)));
        return mongoTemplate.find(query, BoardDoc.class);
    }

    public List<BoardDoc> boardsTitleNe(String title) {
        return boards(Criteria.where("title").ne(title));
    }

    public List<CardDoc> cardsPositionGt(int n) {
        return cards(Criteria.where("position").gt(n));
    }

    public List<CardDoc> cardsPositionGte(int n) {
        return cards(Criteria.where("position").gte(n));
    }

    public List<CardDoc> cardsPositionLt(int n) {
        return cards(Criteria.where("position").lt(n));
    }

    public List<CardDoc> cardsPositionLte(int n) {
        return cards(Criteria.where("position").lte(n));
    }

    public List<CardDoc> cardsPositionBetween(int min, int max) {
        return cards(Criteria.where("position").gt(min).lt(max));
    }

    public List<CardDoc> cardsAssigneeIn(List<String> names) {
        return cards(Criteria.where("assigneeName").in(names));
    }

    public List<BoardDoc> boardsUserNin(List<String> names) {
        return boards(Criteria.where("userName").nin(names));
    }

    public List<CardDoc> cardsOr(String status, String priority) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("status").is(status.toUpperCase()),
                Criteria.where("priority").is(priority.toUpperCase()));
        return cards(criteria);
    }

    public List<CardDoc> cardsAnd(String status, String priority) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("status").is(status.toUpperCase()),
                Criteria.where("priority").is(priority.toUpperCase()));
        return cards(criteria);
    }

    public List<BoardDoc> boardsWithoutDescription() {
        return boards(Criteria.where("description").exists(false));
    }

    public List<CardDoc> cardsCompleted() {
        return cards(Criteria.where("completedAt").exists(true));
    }

    public List<CardDoc> cardsUnassigned() {
        return cards(Criteria.where("assigneeName").exists(false));
    }
}