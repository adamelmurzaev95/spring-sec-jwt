package com.adamelmurzaev.springsecurirysuleymanov.rest;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
