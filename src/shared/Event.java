package shared;

import java.io.Serializable;

public class Event implements Serializable {
    private EventType eventType;
    private Movie payload;

    public Event(EventType eventType, Movie payload) {
        this.eventType = eventType;
        this.payload = payload;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Movie getPayload() {
        return payload;
    }

    public void setPayload(Movie payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventType=" + eventType +
                ", payload=" + payload +
                '}';
    }
}
