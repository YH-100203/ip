package mortis;

import java.util.ArrayList;

public class Parser {
    // Command literals (avoid magic strings)
    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_FIND = "find";

    // Delimiters
    private static final String DELIM_BY = " /by ";
    private static final String DELIM_FROM = " /from ";
    private static final String DELIM_TO = " /to ";

    /**
     * Checks if the given input is the "bye" command.
     *
     * @param input the user input command to check.
     * @return true if the input is "bye", false otherwise.
     */
    public static boolean isBye(String input) {
        return input.equals(CMD_BYE);
    }

    /**
     * Checks if the given input is the "list" command.
     *
     * @param input the user input command to check.
     * @return true if the input is "list", false otherwise.
     */
    public static boolean isList(String input) {
        return input.equals(CMD_LIST);
    }

    /**
     * Parses the index of the task in a "mark" or "unmark" command.
     *
     * @param input the user input command, e.g., "mark 2" or "unmark 3".
     * @param cmd the command type, e.g., "mark" or "unmark".
     * @param taskCount the total number of tasks in the list.
     * @return the zero-based index of the task to mark/unmark.
     * @throws MortisException if the task number is invalid or out of bounds.
     */
    public static int parseIndexAfter(String input, String cmd, int taskCount) throws MortisException {
        // e.g., "mark 2" => returns 1; validates bounds
        String[] parts = input.trim().split("\\s+");
        if (parts.length < 2) {
            throw new MortisException("You must provide a task number to " + cmd + ".");
        }
        int idx;
        try {
            idx = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException ex) {
            throw new MortisException("That is not a valid number, mortal.");
        }
        if (idx < 0 || idx >= taskCount) {
            throw new MortisException("That task does not exist, mortal.");
        }
        assert idx >= 1 && idx <= taskCount : "index out of bounds after parseIndexAfter";
        return idx;
    }

    /**
     * Parses the description of a "todo" task.
     *
     * @param input the user input command, e.g., "todo read book".
     * @return the description of the "todo" task.
     * @throws MortisException if the description is empty or the command is invalid.
     */
    public static String parseTodoDesc(String input) throws MortisException {
        if (!input.startsWith(CMD_TODO)) {
            throw new MortisException("I know not what you mean... try again, mortal.");
        }
        String desc = (input.length() >= 5) ? input.substring(5).trim() : "";
        if (desc.isEmpty()) {
            throw new MortisException("The description of a todo cannot be empty.");
        }

        assert !desc.isEmpty() : "todo description should be non-empty after parse";
        return desc;
    }

    /**
     * Parses a "deadline" task command, extracting the description and due date.
     *
     * @param input the user input command, e.g., "deadline return book /by 2022-10-15".
     * @return an array containing the description and the due date string.
     * @throws MortisException if the command is invalid or incomplete.
     */
    public static String[] parseDeadline(String input) throws MortisException {
        // "deadline <desc> /by <by>"
        if (!input.startsWith(CMD_DEADLINE)) {
            throw new MortisException("I know not what you mean... try again, mortal.");
        }
        String[] parts = input.split(DELIM_BY, 2);
        if (parts.length < 2) {
            throw new MortisException("Deadline must include a /by clause.");
        }
        String desc = (parts[0].length() >= 9) ? parts[0].substring(9).trim() : "";
        if (desc.isEmpty()) {
            throw new MortisException("Deadline description cannot be empty.");
        }
        String by = parts[1].trim();

        assert parts.length == 2 : "deadline parse returns [desc, by]";
        assert !parts[0].isEmpty() && !parts[1].isEmpty() : "deadline fields non-empty";
        return new String[]{desc, by};
    }

    /**
     * Parses an "event" task command, extracting the description, start time, and end time.
     *
     * @param input the user input command, e.g., "event project meeting /from 2022-10-15 10:00 /to 2022-10-15 12:00".
     * @return an array containing the description, start time, and end time of the event.
     * @throws MortisException if the command is invalid or incomplete.
     */
    public static String[] parseEvent(String input) throws MortisException {
        // "event <desc> /from <from> /to <to>"
        if (!input.startsWith(CMD_EVENT)) {
            throw new MortisException("I know not what you mean... try again, mortal.");
        }
        String[] first = input.split(DELIM_FROM, 2);
        if (first.length < 2) {
            throw new MortisException("Event must include a /from time.");
        }
        String desc = (first[0].length() >= 6) ? first[0].substring(6).trim() : "";
        if (desc.isEmpty()) {
            throw new MortisException("Event description cannot be empty.");
        }
        String[] second = first[1].split(DELIM_TO, 2);
        if (second.length < 2) {
            throw new MortisException("Event must include a /to time.");
        }
        String from = second[0].trim();
        String to = second[1].trim();

        return new String[]{desc, from, to};
    }

    /**
     * Parses a "find" command to search for tasks containing a specific keyword.
     *
     * @param fullCommand the user input command, e.g., "find book".
     * @param tasks the task list to search in.
     * @param ui the UI object to display the search results.
     * @throws MortisException if the "find" command is invalid.
     */
    public static void parseFind(String fullCommand, TaskList tasks, Ui ui) {
        // Split the command into its action word and the keyword for finding tasks
        String[] commandParts = fullCommand.split(" ", 2);
        String commandWord = commandParts[0].toLowerCase();

        // If the command is 'find', we execute the find logic
        if (CMD_FIND.equals(commandWord) && commandParts.length > 1) {
            String keyword = commandParts[1];  // Extract the search keyword
            // Find tasks and display them
            ArrayList<Task> matches = tasks.find(keyword);
            ui.showFoundTasks(matches);  // Display the tasks found
        } else {
            // Handle other commands if needed (e.g., 'add', 'delete')
            System.out.println("Unknown or invalid command.");
        }
    }

    public static EditSpec parseEdit(String input) throws MortisException {
        // Example: "edit 2 desc: New event name /by: tomorrow /to: next week"
        String[] parts = input.split("\\s+", 3);
        if (parts.length < 3) {
            throw new MortisException("Edit command must specify fields to update.");
        }

        String updates = parts[2];
        String desc = null, by = null, from = null, to = null;

        if (updates.contains("/desc")) {
            desc = updates.split("desc:")[1].split("/")[0].trim();
        }
        if (updates.contains("/by")) {
            by = updates.split("/by:")[1].split("/")[0].trim();
        }
        if (updates.contains("/from")) {
            from = updates.split("/from:")[1].split("/")[0].trim();
        }
        if (updates.contains("/to")) {
            to = updates.split("/to:")[1].trim();
        }

        return new EditSpec(desc, by, from, to);
    }

}

