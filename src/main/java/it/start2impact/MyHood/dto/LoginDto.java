package it.start2impact.MyHood.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank(message = "Insert your email")
    private String email;
    @NotBlank(message = "Insert your password")
    private String password;

    public LoginDto(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "email: " + this.email;
    }
}
