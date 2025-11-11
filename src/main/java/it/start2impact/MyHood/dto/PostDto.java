package it.start2impact.MyHood.dto;

import it.start2impact.MyHood.enums.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PostDto {
    private Integer id;
    @NotBlank(message = "Please insert a title")
    @Size(message = "The length should be less than 30 character", max = 30)
    private String title;
    private LocalDateTime date;
    @NotBlank(message = "Please insert the content")
    private String content;
    private EventType eventType;

    public PostDto(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "title: " + getTitle() +
                " date: " + getDate() +
                " event type: " + getEventType();
    }
}
