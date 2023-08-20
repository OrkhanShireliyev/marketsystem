package com.example.marketsystempro.repo;

import com.example.marketsystempro.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

}
