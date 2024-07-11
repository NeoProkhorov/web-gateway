package com.neoflex.prokhorov.web_gateway.service.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCredentialsDto {
    String username;
    String password;
}
