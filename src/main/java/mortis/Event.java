package mortis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task, which has a start and end time.
 * Extends the Task class and adds additional properties for the event times.
 */
public class Event extends Task {
    protected String from;
    protected String to;
    private final String EVENT_ICON = "[E]";


    /**
     * Creates an Event task with a description, start time and end time.
     *
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        assert from != null && !from.isBlank() : "'from' must not be blank";
        assert to != null && !to.isBlank() : "'to' must not be blank";
        try {
            LocalDate d1 = LocalDate.parse(from);
            this.from = d1.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        } catch (Exception e) {
            this.from = from;
        }
        try {
            LocalDateTime d2 = LocalDateTime.parse(to);
            this.to = d2.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        } catch (Exception ex) {
            this.to = to;
        }
    }

    /**
     * Provides a string representation of the event, including its status, description and times.
     * @return A string representing the event.
     */
    @Override
    public String toString() {
        String s = EVENT_ICON + super.toString() + " (from: " + from + " to: " + to + ")";
        assert s.startsWith(EVENT_ICON) : "Event toString must start with [E]";
        return s;
    }
}
