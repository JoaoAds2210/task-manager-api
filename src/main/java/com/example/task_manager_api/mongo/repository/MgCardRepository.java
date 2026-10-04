package com.example.task_manager_api.mongo.repository;
 
import com.example.task_manager_api.mongo.document.CardDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
 
public interface MgCardRepository extends MongoRepository<CardDoc, String> {
}