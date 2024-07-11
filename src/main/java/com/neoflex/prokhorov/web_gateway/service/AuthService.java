package com.neoflex.prokhorov.web_gateway.service;

import com.neoflex.prokhorov.web_gateway.client.AccountClient;
import com.neoflex.prokhorov.web_gateway.client.dto.AccountDto;
import com.neoflex.prokhorov.web_gateway.security.AuthorizedUser;
import com.neoflex.prokhorov.web_gateway.security.JwtService;
import com.neoflex.prokhorov.web_gateway.service.dto.JwtTokenDto;
import com.neoflex.prokhorov.web_gateway.service.dto.UserCredentialsDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
    AccountClient accountClient;
    JwtService jwtService;
    AuthenticationManager authenticationManager;

    public JwtTokenDto authenticate(UserCredentialsDto dto) {
        AccountDto accountDto = accountClient.getByUsername(dto.getUsername());

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        AuthorizedUser user = new AuthorizedUser(accountDto);
        String token = jwtService.generateToken(user);
        return new JwtTokenDto(token);
    }
}
