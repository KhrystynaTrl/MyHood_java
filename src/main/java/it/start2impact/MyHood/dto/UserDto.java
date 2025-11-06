package it.start2impact.MyHood.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {
    @NotBlank(message = "Please insert your name")
    @Size(message = "The length should be less than 30 character", max = 30)
    private String name;
    @NotBlank(message = "Please insert your surname")
    @Size(message = "The length should be less than 30 character", max = 30)
    private String surname;
    @NotBlank(message = "Please insert your email")
    @Size(message = "The length should be less than 30 character", max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9]+([._-][0-9a-zA-Z]+)*@[a-zA-Z0-9]+([.-][0-9a-zA-Z]+)*\\.[a-zA-Z]{2,}$\n", message = "Please insert a valid email")
    private String email;
    @NotBlank(message = "Please insert a password")
    private String password;
    @NotBlank(message = "Please insert the location")
    private String location;

    public UserDto(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return  "name: " + getName() +
                "surname: " + getSurname() +
                "location: "+ getLocation() +
                "email: " + getEmail();

    }
}
