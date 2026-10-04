package com.example.task_manager_api.mongo.repository;
 
import com.example.task_manager_api.mongo.document.BoardDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
 
public interface MgBoardRepository extends MongoRepository<BoardDoc, String> {
}