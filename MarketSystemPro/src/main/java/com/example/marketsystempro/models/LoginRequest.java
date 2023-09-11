package com.example.marketsystempro.models;

import lombok.*;

@Getter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
