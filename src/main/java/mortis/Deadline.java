package mortis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task, which has a specific due date.
 * Extends the Task class and adds a field for the deadline date.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a Deadline task with a description and due date.
     *
     * @param description The description of the task.
     * @param by The due date of the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Converts the due date stored in the 'by' field into a formatted string.
     * If the date is valid and can be parsed, it returns the date in the format "MMM d yyyy" (e.g., "Oct 15 2019").
     * If the date cannot be parsed, it returns the original string stored in the 'by' field.
     *
     * @return A string representing the formatted due date if successful, or the original 'by' string if parsing fails.
     */
    public String getFormattedDate() {
        try {
            LocalDate d1 = LocalDate.parse(this.by);
            return d1.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        } catch (Exception e) {
            return this.by;
        }
    }

    /**
     * Provides a string representation of the deadline, including its status, description and due date.
     *
     * @return A string representing the deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getFormattedDate() + ")";
    }
}
