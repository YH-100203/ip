package mortis;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    // Test markAsDone() method
    @Test
    public void testMarkAsDone() {
        // Given
        Task task = new Task("Complete homework");

        // When
        task.markAsDone();

        // Then: Ensure task is marked as done
        assertTrue(task.isDone, "Task should be marked as done after calling markAsDone()");
        assertEquals("X", task.getStatusIcon(), "The status icon should be 'X' when task is done");
    }

    // Test unmark() method
    @Test
    public void testUnmark() {
        // Given
        Task task = new Task("Complete homework");
        task.markAsDone(); // Mark the task as done

        // When
        task.unmark(); // Unmark it

        // Then: Ensure task is marked as not done
        assertFalse(task.isDone, "Task should be marked as not done after calling unmark()");
        assertEquals(" ", task.getStatusIcon(), "The status icon should be ' ' (space) when task is not done");
    }

    // Test markAsDone() after unmark() to ensure the state persists correctly
    @Test
    public void testMarkAsDoneAfterUnmark() {
        // Given
        Task task = new Task("Complete homework");
        task.markAsDone(); // Mark the task as done
        task.unmark();     // Unmark the task

        // When
        task.markAsDone(); // Mark it as done again

        // Then: Ensure task is marked as done again
        assertTrue(task.isDone, "Task should be marked as done after calling markAsDone() again");
        assertEquals("X", task.getStatusIcon(), "The status icon should be 'X' when task is done again");
    }
}
