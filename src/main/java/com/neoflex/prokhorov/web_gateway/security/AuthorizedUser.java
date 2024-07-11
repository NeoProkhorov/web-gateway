package com.neoflex.prokhorov.web_gateway.security;

import com.neoflex.prokhorov.web_gateway.client.dto.AccountDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class AuthorizedUser implements UserDetails {
    static final String DEFAULT_ROLE = "USER";

    AccountDto accountDto;

    public AuthorizedUser(AccountDto accountDto) {
        this.accountDto = accountDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(DEFAULT_ROLE));
    }

    @Override
    public String getPassword() {
        return accountDto.getPassword();
    }

    @Override
    public String getUsername() {
        return accountDto.getLogin();
    }
}
