import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getFormattedDate() {
        try {
            LocalDate d1 = LocalDate.parse(this.by);
            return d1.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        } catch (Exception e) {
            return this.by;
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getFormattedDate() + ")";
    }
}
