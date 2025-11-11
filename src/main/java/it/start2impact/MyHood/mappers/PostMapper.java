package it.start2impact.MyHood.mappers;

import it.start2impact.MyHood.dto.PostDto;
import it.start2impact.MyHood.entities.PostEntity;

public class PostMapper {
    private PostMapper(){}

    public static PostEntity fromDto(PostDto dto){
        PostEntity entity = new PostEntity();
        entity.setTitle(dto.getTitle());
        entity.setPostDate(dto.getDate());
        entity.setContent(dto.getContent());
        entity.setEventType(dto.getEventType());
        return entity;
    }

    public static PostDto fromEntity(PostEntity entity){
        PostDto dto = new PostDto();
        dto.setTitle(entity.getTitle());
        dto.setDate(entity.getPostDate());
        dto.setContent(entity.getContent());
        dto.setEventType(entity.getEventType());
        return dto;
    }
}
