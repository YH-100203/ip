package mortis;

/**
 * Represents a Todo task, which has no specific due date.
 * A simple task with a description and status (done or not)
 */
public class Todo extends Task {
    private static final String TODO_ICON = "[T]";

    /**
     * Creates a Todo task with a description.
     *
     * @param description The description of the task.
     */
    // tasks without any date/time attached to it
    public Todo(String description) {
        super(description);
        assert this.description != null && !this.description.isBlank();
    }

    /**
     * Provides a string representation of the Todo task, including its status and description.
     *
     * @return A string representing a Todo task.
     */
    @Override
    public String toString() {
        String s = TODO_ICON + super.toString();
        assert s.startsWith(TODO_ICON) : "Todo toString must start with [T]";
        return s;
    }
}
