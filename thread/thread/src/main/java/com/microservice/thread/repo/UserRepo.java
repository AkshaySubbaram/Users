package com.microservice.thread.repo;

import com.microservice.thread.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findById(int id);
}
