package com.microservice.thread.service;

import com.microservice.thread.cache.UserCache;
import com.microservice.thread.entity.UserEntity;
import com.microservice.thread.entity.UserJsonEntity;
import com.microservice.thread.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final WebClient jsonPlaceholderClient;

    private final String FILE_PATH  = "D:\\random_users.json";

    public UserService(WebClient.Builder webClientBuilder) {
        this.jsonPlaceholderClient = webClientBuilder.baseUrl("https://randomuser.me").build();
        UserCache.loadUsersIntoCache();
    }

    public Optional<UserEntity> getUserById(Integer userId) {
        return userRepo.findById(userId);
    }

    public Mono<String> fetchRandomUsers() {
        return jsonPlaceholderClient.get().uri("/api/?results=100").retrieve().bodyToMono(String.class).doOnSuccess(this::writeToFile);
    }

    private void writeToFile(String data) {
        try {
            Path filePath = Paths.get(FILE_PATH);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                logger.info("Existing file deleted: {}", FILE_PATH);
            } else {
                Files.createDirectories(filePath.getParent());
                logger.info("Directory created: {}", filePath.getParent());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                writer.write(data);
            }
            logger.info("JSON data written successfully to {} ", FILE_PATH);
        } catch (IOException e) {
            logger.error("Error writing file: {} ", e.getMessage());
        }
    }

    public UserJsonEntity getUserFromCache(String email) {
        return UserCache.getUserByEmail(email);
    }

}
