package com.neoflex.prokhorov.web_gateway.service.dto;

import lombok.Data;

@Data
public class AccountFilterWebDto {
    String surname;
    String name;
    String patronymic;
    String phone;
    String mail;
}
