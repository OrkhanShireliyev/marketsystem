package com.example.marketsystempro.service;

import com.company.marketsystem.models.UserEntity;
import com.company.marketsystem.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    public Optional<UserEntity> findByEmail(String username){
        if(!userRepo.findByEmail(username).isEmpty()) {
            return userRepo.findByEmail(username);
        }
        return Optional.empty();
    }
    public void saveUser(UserEntity user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepo.save(user);
    }
}
