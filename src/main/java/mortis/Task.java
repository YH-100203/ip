package mortis;


/**
 * Represents a Task with a description and a status (done or not).
 * Provides methods to mark the task as done or undone, get the status, and get the task description.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor to create a new Task with the given description.
     * Initially, the task is not marked as done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null : "Task description must not be null";
        assert !description.isBlank() : "Task description must not be blank";
        this.description = description;
        // class invariant after construction
        assert this.description != null && !this.description.isBlank();
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        boolean prev = isDone;
        isDone = true;
        assert isDone : "Task should be marked done after markAsDone()";
        assert prev || isDone; // sanity: state moved towards done
    }

    /**
     * Unmarks the task, setting its status to undone.
     */
    public void unmark() {
        boolean prev = isDone;
        isDone = false;
        assert !isDone : "Task should be not done after unmark()";
        assert !prev || !isDone; // sanity: state moved towards not-done
    }

    /**
     * Returns the icon representing the task status.
     * 'X' if the task is done, a space if the task is not done.
     *
     * @return A string representing the task status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        assert description != null && !description.isBlank() : "description invariant";
        return description;
    }


    /**
     * Provides a string representation of the task, including its status and description.
     *
     * @return A string representing the task in the format [status] description.
     */
    public String toString() {
        String s = "[" + getStatusIcon() + "] " + description;
        assert s != null && !s.isBlank() : "toString must return non-empty";
        return s;
    }


}
