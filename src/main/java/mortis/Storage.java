package mortis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Handles the loading and saving of tasks to and from a file.
 * It manages file operations such as creating directories, reading and writing data, and handling errors.
 */
public class Storage {
    private final Path path;

    /**
     * Constructs a Storage object that handles file operations at the specified path.
     *
     * @param filePath The path to the file where task data will be stored or retrieved.
     */
    public Storage(String filePath) {
        this.path = Paths.get(filePath);
        assert filePath != null && !filePath.isEmpty() : "storage file path set";
    }

    /**
     * Loads tasks from the file specified by the path.
     * If the file does not exist, a new file will be created.
     *
     * @return A list of tasks loaded from the file.
     * @throws MortisException If an error occurs while loading tasks from the file.
     */
    public ArrayList<Task> load() throws MortisException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
                assert Files.exists(path.getParent()) : "data directory exists";
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
                return tasks;
            }
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Expected formats:
                    // T | 1 | desc
                    // D | 0 | desc | by
                    // E | 1 | desc | from | to
                    String[] parts = line.split(" \\| ");
                    if (parts.length < 3) continue; // skip malformed
                    String type = parts[0].trim();
                    boolean done = parts[1].trim().equals("1");
                    String desc = parts[2].trim();
                    assert type.equals("T") ||  type.equals("D") ||  type.equals("E") : "known record type";

                    Task t;
                    switch (type) {
                        case "T":
                            t = new Todo(desc);
                            break;
                        case "D":
                            if (parts.length < 4) continue;
                            t = new Deadline(desc, parts[3].trim());
                            break;
                        case "E":
                            if (parts.length < 5) continue;
                            t = new Event(desc, parts[3].trim(), parts[4].trim());
                            break;
                        default:
                            continue; // unknown type
                    }
                    if (done) t.markAsDone();
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            throw new MortisException("Error loading task data.");
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to the file.
     *
     * @param list The list of tasks to be saved to the file.
     */
    public void save(TaskList list) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                for (Task task : list.asList()) {
                    if (task instanceof Todo) {
                        writer.write("T | " + (task.isDone ? "1" : "0") + " | " + task.getDescription());
                    } else if (task instanceof Deadline) {
                        writer.write("D | " + (task.isDone ? "1" : "0") + " | " + task.getDescription()
                                + " | " + ((Deadline) task).by);
                    } else if (task instanceof Event) {
                        writer.write("E | " + (task.isDone ? "1" : "0") + " | " + task.getDescription()
                                + " | " + ((Event) task).from + " | " + ((Event) task).to);
                    } else {
                        continue;
                    }
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     An error occurred while saving the task data.");
            System.out.println("    ____________________________________________________________");
        }
    }
}


