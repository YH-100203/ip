package mortis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
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

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to:" + to + ")";
    }
}
