package com.microservice.thread.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.thread.entity.UserJsonEntity;
import com.microservice.thread.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserCache {
    private static final Logger logger = LoggerFactory.getLogger(UserCache.class);
    private static final String FILE_PATH = "D:\\random_users.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Map<String, UserJsonEntity> userCache = new ConcurrentHashMap<>();

    static {
        loadUsersIntoCache();
    }

    public static void loadUsersIntoCache() {
        try {
            UserResponse userResponse = objectMapper.readValue(new File(FILE_PATH), UserResponse.class);
            userResponse.getResults().forEach(user -> userCache.put(user.getEmail(), user));
            logger.info("User data loaded into cache. Total Users: {}", userCache.size());
        } catch (IOException e) {
            logger.error("Error loading user data: {}", e.getMessage());
        }
    }

    public static UserJsonEntity getUserByEmail(String email) {
        return userCache.get(email);
    }

}
