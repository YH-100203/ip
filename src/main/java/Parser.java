public class Parser {

    public static boolean isBye(String input) {
        return input.equals("bye");
    }

    public static boolean isList(String input) {
        return input.equals("list");
    }

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
        return idx;
    }

    public static String parseTodoDesc(String input) throws MortisException {
        if (!input.startsWith("todo")) {
            throw new MortisException("I know not what you mean... try again, mortal.");
        }
        String desc = (input.length() >= 5) ? input.substring(5).trim() : "";
        if (desc.isEmpty()) {
            throw new MortisException("The description of a todo cannot be empty.");
        }
        return desc;
    }

    public static String[] parseDeadline(String input) throws MortisException {
        // "deadline <desc> /by <by>"
        if (!input.startsWith("deadline")) {
            throw new MortisException("I know not what you mean... try again, mortal.");
        }
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2) {
            throw new MortisException("Deadline must include a /by clause.");
        }
        String desc = (parts[0].length() >= 9) ? parts[0].substring(9).trim() : "";
        if (desc.isEmpty()) {
            throw new MortisException("Deadline description cannot be empty.");
        }
        String by = parts[1].trim();
        return new String[]{desc, by};
    }

    public static String[] parseEvent(String input) throws MortisException {
        // "event <desc> /from <from> /to <to>"
        if (!input.startsWith("event")) {
            throw new MortisException("I know not what you mean... try again, mortal.");
        }
        String[] first = input.split(" /from ", 2);
        if (first.length < 2) {
            throw new MortisException("Event must include a /from time.");
        }
        String desc = (first[0].length() >= 6) ? first[0].substring(6).trim() : "";
        if (desc.isEmpty()) {
            throw new MortisException("Event description cannot be empty.");
        }
        String[] second = first[1].split(" /to", 2);
        if (second.length < 2) {
            throw new MortisException("Event must include a /to time.");
        }
        String from = second[0].trim();
        String to = second[1].trim();
        return new String[]{desc, from, to};
    }
}

