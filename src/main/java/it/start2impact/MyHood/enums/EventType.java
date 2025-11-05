package it.start2impact.MyHood.enums;

public enum EventType {
    SPORT("sport"),
    MUSIC("music"),
    BOOK_EXCHANGE("book exchange"),
    FOOD("food"),
    OTHER("other");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
