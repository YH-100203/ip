package mortis;

/**
 * Represents a Todo task, which has no specific due date.
 * A simple task with a description and status (done or not)
 */
public class Todo extends Task {

    /**
     * Creates a Todo task with a description.
     *
     * @param description The description of the task.
     */
    // tasks without any date/time attached to it
    public Todo(String description) {
        super(description);
    }

    /**
     * Provides a string representation of the Todo task, including its status and description.
     *
     * @return A string representing a Todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
