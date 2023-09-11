package com.example.marketsystempro.controller;


import com.company.marketsystem.dto.ResponseDto;
import com.company.marketsystem.dto.RoleDto;
import com.company.marketsystem.dto.UserDto;
import com.company.marketsystem.models.LoginRequest;
import com.company.marketsystem.models.Role;
import com.company.marketsystem.models.UserEntity;
import com.company.marketsystem.service.AuthService;
import com.company.marketsystem.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Log4j2
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity<RoleDto> login(@RequestBody String request) throws JsonProcessingException {
        LoginRequest loginRequest=objectMapper.readValue(request, LoginRequest.class);
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1l);
        List<Role> roleList=userService.findByEmail(loginRequest.getEmail()).get().getRoles();
        List<String> roles=new ArrayList<>();
        for (Role role: roleList
        ) {
            roles.add(role.name());
        }
        roleDto.setRoles(roles);
        roleDto.setToken(authService.loginResponse(loginRequest.getEmail(),loginRequest.getPassword()));
        System.out.println("login oldu token verildi");
        return ResponseEntity.ok(roleDto);
    }


    @PostMapping("/register")
        public ResponseDto register(@RequestBody String register) throws IOException {
        UserDto userDto = objectMapper.readValue(register, UserDto.class);
        UserEntity userEntity = objectMapper.convertValue(userDto, UserEntity.class);
        if(!userEntity.getEmail().contains("@")){
            return ResponseDto.builder().statusString("emailinizi duzgun daxil edin").build();
        }else {
            List<Role> roleList = new ArrayList<>();
            if (userEntity.getEmail().startsWith("mng")) {
                roleList.add(Role.ROLE_SUPERVISOR);
                roleList.add(Role.ROLE_ADMIN);
//                System.out.println("supervisior rolu verildi");
//                System.out.println("admin rolu verildi");
                log.info("supervisior rolu verildi");
            } else if (userEntity.getEmail().startsWith("assistant")) {
                roleList.add(Role.ROLE_ADMIN);
                System.out.println("admin rolu verildi");
            } else {
                roleList.add(Role.ROLE_USER);
                System.out.println("user rolu verildi");
            }
            userEntity.setRoles(roleList);
        }
        userService.saveUser(userEntity);
        return ResponseDto.builder().id(1l).statusString("Ugurla register oldunuz").build();
    }
}
