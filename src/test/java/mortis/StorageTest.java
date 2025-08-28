package mortis;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StorageTest {
    // Test save() method from Storage
    @Test
    public void testSaveTasksToFile() throws IOException, MortisException {
        // Given
        String filePath = "data/duke.txt";
        Storage storage = new Storage(filePath);
        Task task1 = new Todo("Complete homework");
        task1.markAsDone();
        Task task2 = new Deadline("Return book", "2023-10-10");
        task2.unmark();
        Task task3 = new Event("Project meeting", "2023-10-01 10:00", "2023-10-01 12:00");
        task3.markAsDone();

        TaskList taskList = new TaskList();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        // When: Save the tasks to file
        storage.save(taskList);

        // Then: Ensure that the tasks are saved correctly
        // Reload the tasks
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(3, loadedTasks.size(), "The number of tasks loaded should match the number saved");

        // Validate individual tasks
        assertTrue(loadedTasks.get(0) instanceof Todo, "First task should be a Todo");
        assertEquals("Complete homework", loadedTasks.get(0).getDescription(), "The description of the first task should match");

        assertTrue(loadedTasks.get(1) instanceof Deadline, "Second task should be a Deadline");
        assertEquals("Return book", loadedTasks.get(1).getDescription(), "The description of the second task should match");

        assertTrue(loadedTasks.get(2) instanceof Event, "Third task should be an Event");
        assertEquals("Project meeting", loadedTasks.get(2).getDescription(), "The description of the third task should match");

        // Clean up: Remove the temporary file after the test
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    // Test load() with an empty file
    @Test
    public void testLoadEmptyFile() throws IOException, MortisException {
        // Given: An empty file (create a new file for the test)
        String filePath = "data/empty.txt";
        Storage storage = new Storage(filePath);

        // When: Load tasks from the empty file
        ArrayList<Task> tasks = storage.load();

        // Then: Ensure that no tasks are loaded
        assertNotNull(tasks, "Tasks should be loaded from the file");
        assertTrue(tasks.isEmpty(), "The loaded tasks should be empty if the file is empty");

        // Clean up: Remove the empty test file after the test
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    // Test save() with an empty list of tasks
    @Test
    public void testSaveEmptyTaskList() throws IOException, MortisException {
        // Given: An empty task list
        String filePath = "data/duke.txt";
        Storage storage = new Storage(filePath);
        TaskList taskList = new TaskList();

        // When: Save the empty task list to file
        storage.save(taskList);

        // Then: Ensure the file is empty
        ArrayList<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty(), "The file should be empty when no tasks are saved");

        // Clean up: Remove the temporary file after the test
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}


