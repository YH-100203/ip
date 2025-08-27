import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.*;
import java.io.*;

public class Mortis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Greetings, mortal. I am Mortis, your eternal assistant.");
        System.out.println(" What dark secret may I help you uncover today?");
        System.out.println("____________________________________________________________");

        // Use ArrayList to store tasks (instead of fixed-size array)
        ArrayList<Task> tasks = loadTasksFromFile();

        // Echo loop
        while (true) {
            // Standard code for input
            String input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     Farewell, traveler. Mortis shall await your return...");
                    System.out.println("    ____________________________________________________________");
                    break;
                } else if (input.equals("list")) {
                    System.out.println("    ____________________________________________________________");
                    if (tasks.isEmpty()) {
                        System.out.println("     Mortis has not yet received any tasks... *sadness*");
                    } else {
                        System.out.println("     Mortis’ records of your tasks:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println("     " + (i + 1) + "." + tasks.get(i).toString());
                        }
                    }
                    System.out.println("    ____________________________________________________________");
                } else if (input.startsWith("mark")) { // Usage of startsWith to get keyword
                    int taskId = Integer.parseInt(input.split(" ")[1]) - 1; // Get task ID
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new MortisException("You must provide a task number to mark.");
                    }
                    if (taskId < 0 || taskId >= tasks.size()) {
                        throw new MortisException("That task does not exist, mortal.");
                    }
                    tasks.get(taskId).markAsDone();
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     Ah... the task is now done. The darkness has claimed it:");
                    System.out.println("       " + tasks.get(taskId).toString());
                    System.out.println("    ____________________________________________________________");
                    saveTasksToFile(tasks);
                } else if (input.startsWith("unmark")) {
                    // Unmark task as not done
                    int taskId = Integer.parseInt(input.split(" ")[1]) - 1; // Get task ID
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new MortisException("You must provide a task number to unmark.");
                    }
                    if (taskId < 0 || taskId >= tasks.size()) {
                        throw new MortisException("That task does not exist, mortal.");
                    }
                    tasks.get(taskId).unmark();
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     OK... I've pulled the task back from the abyss. It is undone now:");
                    System.out.println("       " + tasks.get(taskId).toString());
                    System.out.println("    ____________________________________________________________");
                    saveTasksToFile(tasks);
                } else if (input.startsWith("delete")) {
                    int taskId = Integer.parseInt(input.split(" ")[1]) - 1; // Get task ID
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new MortisException("You must provide a task number to delete.");
                    }
                    if (taskId < 0 || taskId >= tasks.size()) {
                        throw new MortisException("That task does not exist, mortal.");
                    }
                    Task deletedTask = tasks.remove(taskId);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     Noted. I've removed this task from the abyss");
                    System.out.println("       " + deletedTask.toString());
                    System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    saveTasksToFile(tasks);
                } else if (input.startsWith("todo")) {
                    String taskDescription = input.substring(5); // Extract description
                    if (taskDescription.isEmpty()) {
                        throw new MortisException("The description of a todo cannot be empty.");
                    }
                    tasks.add(new Todo(taskDescription));
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     Mortis notes your tasks:");
                    System.out.println("       " + tasks.get(tasks.size() - 1).toString());
                    System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    saveTasksToFile(tasks);
                } else if (input.startsWith("deadline")) {
                    String[] parts = input.split(" /by ");
                    if (parts.length < 2) {
                        throw new MortisException("Deadline must include a /by clause.");
                    }
                    String taskDescription = parts[0].substring(9); // Extract description
                    if (taskDescription.isEmpty()) {
                        throw new MortisException("Deadline description cannot be empty.");
                    }
                    String deadline = parts[1]; // Extract deadline time
                    tasks.add(new Deadline(taskDescription, deadline));
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     Mortis notes your tasks:");
                    System.out.println("       " + tasks.get(tasks.size() - 1).toString());
                    System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    saveTasksToFile(tasks);
                } else if (input.startsWith("event")) {
                    String[] parts = input.split(" /from ");
                    if (parts.length < 2) {
                        throw new MortisException("Event must include a /from time.");
                    }
                    String taskDescription = parts[0].substring(6); // Extract description
                    if (taskDescription.isEmpty()) {
                        throw new MortisException("Event description cannot be empty.");
                    }
                    String[] timeParts = parts[1].split(" /to");
                    if (timeParts.length < 2) {
                        throw new MortisException("Event must include a /to time.");
                    }
                    String fromTime = timeParts[0]; // Extract start time
                    String toTime = timeParts[1]; // Extract end time
                    tasks.add(new Event(taskDescription, fromTime, toTime));
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     Mortis notes your tasks:");
                    System.out.println("       " + tasks.get(tasks.size() - 1).toString());
                    System.out.println("     Now you have " + tasks.size()+ " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    saveTasksToFile(tasks);
                } else {
                    throw new MortisException("I know not what you mean... try again, mortal.");
                }
            } catch (MortisException e) {
                System.out.println("    ____________________________________________________________");
                System.out.println("     " + e.getMessage());
                System.out.println("    ____________________________________________________________");
            } catch (NumberFormatException e) {
                System.out.println("    ____________________________________________________________");
                System.out.println("     That is not a valid number, mortal.");
                System.out.println("    ____________________________________________________________");
            }
        }

        sc.close();
    }

    // Method to save tasks to file
    private static void saveTasksToFile(ArrayList<Task> tasks) {
        try {
            Path path = Paths.get("./data/duke.txt"); // Create a path object pointing to that path
            Files.createDirectories(path.getParent());  // Ensure the directory exists, if not create it
            BufferedWriter writer = Files.newBufferedWriter(path); // Creates a buffered writer for writing to file

            for (Task task : tasks) {
                // Handling each type of event
                // Can also just use task.toString() but will be different format
                if (task instanceof Todo) {
                    writer.write("T | " + (task.isDone ? "1" : "0") + " | " + task.getDescription() + "\n");
                } else if (task instanceof Deadline) {
                    writer.write("D | " + (task.isDone ? "1" : "0") + " | " + task.getDescription() + " | " + ((Deadline) task).by + "\n");
                } else if (task instanceof Event) {
                    writer.write("E | " + (task.isDone ? "1" : "0") + " | " + task.getDescription() + " | " + ((Event) task).from + " | " + ((Event) task).to + "\n");
                }
            }

            writer.close();
        } catch (IOException e) {
            // If any IOException occurs during the file handling process
            // (e.g., if the file is not writable or if there's an issue with the file system),
            // the program will catch the exception and print an error message.
            System.out.println("    ____________________________________________________________");
            System.out.println("     An error occurred while saving the task data.");
            System.out.println("    ____________________________________________________________");
        }
    }

    // Method to load tasks from file
    private static ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Path path = Paths.get("./data/duke.txt");

            if (Files.exists(path)) {
                BufferedReader reader = Files.newBufferedReader(path); // creates a Buffered reader to read the contents
                String line; // Used to store each line read from the file

                while ((line = reader.readLine()) != null) { // Reads the file line by line until there are no more lines
                    String[] parts = line.split(" \\| ");
                    if (parts.length < 3) continue;  // Skip malformed lines

                    // processing the tasks and repopulating the tasks array
                    String taskType = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];

                    if (taskType.equals("T")) {
                        tasks.add(new Todo(description));
                    } else if (taskType.equals("D")) {
                        String by = parts[3];
                        tasks.add(new Deadline(description, by));
                    } else if (taskType.equals("E")) {
                        String from = parts[3];
                        String to = parts[4];
                        tasks.add(new Event(description, from, to));
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Error loading task data.");
            System.out.println("    ____________________________________________________________");
        }

        return tasks;
    }
}
