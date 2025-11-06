package it.start2impact.MyHood.mappers;

import it.start2impact.MyHood.dto.UserDto;
import it.start2impact.MyHood.entities.UserEntity;

public class UserMapper {
    private UserMapper(){}

    public static UserEntity fromDTO(UserDto dto){
        UserEntity entity = new UserEntity();
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    public static UserDto fromEntity(UserEntity entity){
        UserDto dto = new UserDto();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
