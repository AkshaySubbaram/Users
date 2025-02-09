package com.microservice.thread.cache;

import com.microservice.thread.entity.UserJsonEntity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUserCache {

    private static final Logger logger = LoggerFactory.getLogger(TestUserCache.class);

    @Test
    public void checkDataIsCached() {
        String testEmail = "hermann.griese@example.com";
        UserJsonEntity user = UserCache.getUserByEmail(testEmail);

        if (user != null) {
            logger.info("User found in cache: {}", user.getEmail());
        } else {
            logger.info("User not found in cache.");
        }
    }
}
