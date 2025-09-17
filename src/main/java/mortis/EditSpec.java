package mortis;

/**
 * This file includes Javadoc comments generated with the help of OpenAI's ChatGPT (GPT-5).
 * The AI tool was used to:
 * - Suggest clear, concise Javadoc descriptions for the class, constructor, and getter methods.
 * - Ensure consistent formatting and alignment with common Java documentation conventions.
 * The underlying code (fields, constructor, getters) was written manually,
 * while the comments were AI-assisted to improve readability and maintainability.
 */

/**
 * Represents the specifications for editing a task.
 * This class stores the new values for description, deadlines,
 * and time ranges that may be updated during an edit operation.
 */
public class EditSpec {
    private final String newDescription;
    private final String newBy;
    private final String newFrom;
    private final String newTo;

    /**
     * Constructs an EditSpec with the provided new values.
     *
     * @param newDescription the updated description of the task
     * @param newBy the updated "by" deadline (single date/time)
     * @param newFrom the updated start time (for ranged tasks)
     * @param newTo the updated end time (for ranged tasks)
     */
    public EditSpec(String newDescription, String newBy, String newFrom, String newTo) {
        this.newDescription = newDescription;
        this.newBy = newBy;
        this.newFrom = newFrom;
        this.newTo = newTo;
    }

    /**
     * Gets the updated description for the task.
     *
     * @return the new description
     */
    public String getNewDescription() {
        return newDescription;
    }

    /**
     * Gets the updated "by" deadline for the task.
     *
     * @return the new "by" deadline
     */
    public String getNewBy() {
        return newBy;
    }

    /**
     * Gets the updated start time for the task.
     *
     * @return the new start time
     */
    public String getNewFrom() {
        return newFrom;
    }

    /**
     * Gets the updated end time for the task.
     *
     * @return the new end time
     */
    public String getNewTo() {
        return newTo;
    }
}

