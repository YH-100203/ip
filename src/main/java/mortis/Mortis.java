package mortis;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for running the Duke application.
 * Responsible for initializing the storage, task list, and UI, and running the application loop.
 */
public class Mortis {

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a Mortis instance with the specified file path.
     *
     * @param filePath The path to the file to load/save tasks.
     */
    public Mortis(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (MortisException e) {
            ui.showError(e.getMessage());
            loaded = new TaskList();
        }
        this.tasks = loaded;

        // internal invariants
        assert ui != null : "UI must be initialized";
        assert storage != null : "Storage must be initialized";
        assert tasks != null : "TaskList must be initialized";
    }

    public String getResponse(String rawInput) {
        final String input = (rawInput == null ? "" : rawInput.trim());
        try {
            if (Parser.isBye(input)) {
                storage.save(tasks);
                return "Farewell. Mortis slumbers...";
            } else if (Parser.isList(input)) {
                return formatTaskList();
            } else if (input.startsWith("mark")) { // consider normalizing case
                int idx = Parser.parseIndexAfter(input, "mark", tasks.size());
                assert idx >= 0 && idx < tasks.size() : "mark index must be valid";
                Task t = tasks.mark(idx);
                assert t != null : "Marked task must not be null";
                storage.save(tasks);
                return "Ah... the task is now done. The darkness has claimed it:\n  " + t;
            } else if (input.startsWith("unmark")) { // consider normalizing case
                int idx = Parser.parseIndexAfter(input, "unmark", tasks.size());
                assert idx >= 0 && idx < tasks.size() : "unmark index must be valid";
                Task t = tasks.unmark(idx);
                assert t != null : "Unmarked task must not be null";
                storage.save(tasks);
                return "OK... I've pulled the task back from the abyss. It is undone now:\n  " + t; // no Markdown
            } else if (input.startsWith("delete")) {
                int idx = Parser.parseIndexAfter(input, "delete", tasks.size());
                assert idx >= 0 && idx < tasks.size() : "delete index must be valid";
                Task deleted = tasks.delete(idx);
                assert deleted != null : "deleted task must not be null";
                storage.save(tasks);
                return "Removed this task:\n  " + deleted + "\n"
                        + "Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list.";
            } else if (input.startsWith("todo")) {
                String desc = Parser.parseTodoDesc(input);
                assert desc != null && !desc.isBlank() : "Todo description must not be empty";
                Task added = tasks.add(new Todo(desc));
                storage.save(tasks);
                return "Added this task:\n  " + added + "\n"
                        + "Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list.";
            } else if (input.startsWith("deadline")) {
                String[] p = Parser.parseDeadline(input); // [desc, by]
                assert p.length == 2 : "Deadline must return [desc, by]";
                Task added = tasks.add(new Deadline(p[0], p[1]));
                storage.save(tasks);
                return "Added this task:\n  " + added + "\n"
                        + "Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list.";
            } else if (input.startsWith("event")) {
                String[] p = Parser.parseEvent(input); // [desc, from, to]
                assert p.length == 3 : "Event must return [desc, from, to]";
                Task added = tasks.add(new Event(p[0], p[1], p[2]));
                storage.save(tasks);
                return "Added this task:\n  " + added + "\n"
                        + "Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list.";
            } else if (input.startsWith("find")) {
                String keyword = input.length() >= 5 ? input.substring(5).trim() : "";
                if (keyword.isEmpty()) {
                    throw new MortisException("Provide a keyword for me to seek.");
                }
                return formatFindResults(keyword);
            } else {
                throw new MortisException("I know not what you mean... try again, mortal.");
            }
        } catch (MortisException e) {
            return e.getMessage();
        } catch (Exception e) {
            return (e.getMessage() != null && !e.getMessage().isBlank())
                    ? e.getMessage()
                    : ("Something went awry: " + e.getClass().getSimpleName());
        }
    }

    private String formatTaskList() {
        if (tasks.size() == 0) {
            return "Mortis has not yet received any tasks.";
        }
        StringBuilder sb = new StringBuilder("Mortis’ records of your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i); // uses TaskList#get(int)
            assert t != null : "Task must not be null when formatting list";
            sb.append(i + 1).append(". ").append(t).append('\n');
        }
        return sb.toString().trim();
    }

    private String formatFindResults(String keyword) {
        assert keyword != null && !keyword.isBlank() : "Find keyword must not be null or blank";
        String needle = keyword.toLowerCase();

        // Prefer a simple linear scan using TaskList#size and #get(int)
        List<Task> matches = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            assert t != null : "Task must not be null when searching";
            // Use the dedicated accessor rather than toString() for matching
            if (t.getDescription().toLowerCase().contains(needle)) {
                matches.add(t);
            }
        }

        if (matches.isEmpty()) {
            return "No tasks match: \"" + keyword + "\"";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks found mortal:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1).append(". ").append(matches.get(i)).append('\n');
        }
        return sb.toString().trim();
    }

    public boolean isExit(String input) {
        // true when user typed "bye" (or your equivalent)
        return Parser.isBye(input == null ? "" : input.trim());
    }

    /**
     * Starts the application by running the main loop.
     */
    public void run() {
        ui.showWelcome();
        boolean exit = false;
        while (!exit) {
            String input = ui.readCommand().trim();
            try {
                if (Parser.isBye(input)) {
                    ui.showBye();
                    exit = true;
                } else if (Parser.isList(input)) {
                    ui.showList(tasks);
                } else if (input.startsWith("mark")) {
                    int idx = Parser.parseIndexAfter(input, "mark", tasks.size());
                    assert idx >= 0 && idx < tasks.size() : "mark index must be valid";
                    Task t = tasks.mark(idx);
                    assert t != null : "Marked task must not be null";
                    storage.save(tasks);
                    ui.showMarked(t);
                } else if (input.startsWith("unmark")) {
                    int idx = Parser.parseIndexAfter(input, "unmark", tasks.size());
                    assert idx >= 0 && idx < tasks.size() : "mark index must be valid";
                    Task t = tasks.unmark(idx);
                    assert t != null : "Marked task must not be null";
                    storage.save(tasks);
                    ui.showUnmarked(t);
                } else if (input.startsWith("delete")) {
                    int idx = Parser.parseIndexAfter(input, "delete", tasks.size());
                    assert idx >= 0 && idx < tasks.size() : "delete index must be valid";
                    Task deleted = tasks.delete(idx);
                    assert deleted != null : "Deleted task must not be null";
                    storage.save(tasks);
                    ui.showDelete(deleted, tasks.size());
                } else if (input.startsWith("todo")) {
                    String desc = Parser.parseTodoDesc(input);
                    assert desc != null && !desc.isBlank() : "Todo description must not be empty";
                    Task added = tasks.add(new Todo(desc));
                    storage.save(tasks);
                    ui.showAdd(added, tasks.size());
                } else if (input.startsWith("deadline")) {
                    String[] p = Parser.parseDeadline(input);
                    assert p.length == 2 : "Deadline must return [desc, by]";
                    Task added = tasks.add(new Deadline(p[0], p[1]));
                    storage.save(tasks);
                    ui.showAdd(added, tasks.size());
                } else if (input.startsWith("event")) {
                    String[] p = Parser.parseEvent(input);
                    assert p.length == 3 : "Event must return [desc, from, to]";
                    Task added = tasks.add(new Event(p[0], p[1], p[2]));
                    storage.save(tasks);
                    ui.showAdd(added, tasks.size());
                } else if (input.startsWith("find")) {
                    String keyword = input.substring(5).trim();
                    if (!keyword.isEmpty()) {
                        Parser.parseFind("find " + keyword, tasks, ui);
                    } else {
                        ui.showError("Provide a keyword for me to seek.");
                    }
                }
                else {
                    throw new MortisException("I know not what you mean... try again, mortal.");
                }
            } catch (MortisException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Main method to run the application.
     *
     * @param args Command-line arguments (not used in this version).
     */
    public static void main(String[] args) {
        // Keep relative, cross-OS safe path
        new Mortis("data/duke.txt").run();
    }
}
