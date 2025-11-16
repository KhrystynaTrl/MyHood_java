package it.start2impact.MyHood.dto;

import it.start2impact.MyHood.enums.EventType;

import java.time.LocalDate;

public class FilterDto {
    private LocalDate from;
    private LocalDate to;
    private EventType eventType;
    private String location;

    public FilterDto(){}

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
