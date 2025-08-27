import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        LocalDate d1 = LocalDate.parse(by);
        this.by = d1.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }



    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
