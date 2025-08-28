package mortis;

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
                    Task t = tasks.mark(idx);
                    storage.save(tasks);
                    ui.showMarked(t);
                } else if (input.startsWith("unmark")) {
                    int idx = Parser.parseIndexAfter(input, "unmark", tasks.size());
                    Task t = tasks.unmark(idx);
                    storage.save(tasks);
                    ui.showUnmarked(t);
                } else if (input.startsWith("delete")) {
                    int idx = Parser.parseIndexAfter(input, "delete", tasks.size());
                    Task deleted = tasks.delete(idx);
                    storage.save(tasks);
                    ui.showDelete(deleted, tasks.size());
                } else if (input.startsWith("todo")) {
                    String desc = Parser.parseTodoDesc(input);
                    Task added = tasks.add(new Todo(desc));
                    storage.save(tasks);
                    ui.showAdd(added, tasks.size());
                } else if (input.startsWith("deadline")) {
                    String[] p = Parser.parseDeadline(input);
                    Task added = tasks.add(new Deadline(p[0], p[1]));
                    storage.save(tasks);
                    ui.showAdd(added, tasks.size());
                } else if (input.startsWith("event")) {
                    String[] p = Parser.parseEvent(input);
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
