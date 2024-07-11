package com.neoflex.prokhorov.web_gateway.security;

import com.neoflex.prokhorov.web_gateway.client.AccountClient;
import com.neoflex.prokhorov.web_gateway.client.dto.AccountDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {
    AccountClient accountClient;
    JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDto dto = accountClient.getByUsername(username);
        return new AuthorizedUser(dto);
    }

    public AuthorizedUser loadUserByToken(String token) throws UsernameNotFoundException {
        String login = jwtService.getUsername(token);
        return (AuthorizedUser) loadUserByUsername(login);
    }
}
