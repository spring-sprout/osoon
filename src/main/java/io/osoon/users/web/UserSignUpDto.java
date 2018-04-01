package io.osoon.users.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UserSignUpDto {

    @Email
    private String email;

    @NotEmpty
    private String password;

}
