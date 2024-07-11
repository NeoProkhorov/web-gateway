package com.neoflex.prokhorov.web_gateway.web;

import com.neoflex.prokhorov.web_gateway.service.AuthService;
import com.neoflex.prokhorov.web_gateway.service.dto.JwtTokenDto;
import com.neoflex.prokhorov.web_gateway.service.dto.UserCredentialsDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService service;

    @PostMapping("/login")
    JwtTokenDto authenticate(@RequestBody UserCredentialsDto userCredentialsDto) {
        return service.authenticate(userCredentialsDto);
    }
}
