package com.example.chatserver.reps;

import com.example.chatserver.dto.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface IUserRepository extends MongoRepository<MongoUser, String> {
    MongoUser findByUsername(String name);
}
