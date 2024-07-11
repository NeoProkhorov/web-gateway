package com.neoflex.prokhorov.web_gateway.client.dto;

import lombok.Data;

@Data
public class AccountFilterDto {
    String surname;
    String name;
    String patronymic;
    String phone;
    String mail;
}
