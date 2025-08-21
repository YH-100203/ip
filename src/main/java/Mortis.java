import java.util.Scanner;

public class Mortis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Greetings, mortal. I am Mortis, your eternal assistant.");
        System.out.println(" What dark secret may I help you uncover today?");
        System.out.println("____________________________________________________________");

        // Task list to store user input
        Task[] tasks = new Task[100]; // Task list to store user input
        int taskCount = 0;

        // Echo loop
        while (true) {
            // Standard code for input
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("    ____________________________________________________________");
                System.out.println("     Farewell, traveler. Mortis shall await your return...");
                System.out.println("    ____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("    ____________________________________________________________");
                System.out.println("     Mortis’ records of your tasks:");
                for (int i = 0; i < taskCount; i++) {
                    Task target = tasks[i];
                    System.out.println("     " + (i+1) + "." + tasks[i].toString());
                }
                System.out.println("    ____________________________________________________________");
            } else if (input.startsWith("mark")) { // Usage of startsWith to get keyword
                int taskId = Integer.parseInt(input.split(" ")[1]) - 1; // Get task ID
                if (taskId >= 0 && taskId < taskCount) {
                    tasks[taskId].markAsDone();
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     Ah... the task is now done. The darkness has claimed it:");
                    System.out.println("       [X] " + tasks[taskId].getDescription());
                    System.out.println("    ____________________________________________________________");
                }
            } else if (input.startsWith("unmark")) {
                // Unmark task as not done
                int taskId = Integer.parseInt(input.split(" ")[1]) - 1; // Get task ID
                if (taskId >= 0 && taskId < taskCount) {
                    tasks[taskId].unmark();
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     OK... I've pulled the task back from the abyss. It is undone now:");
                    System.out.println("       " + tasks[taskCount - 1].toString());
                    System.out.println("    ____________________________________________________________");
                }
            } else if (input.startsWith("todo")) {
                String taskDescription = input.substring(5); // Extract description
                tasks[taskCount] = new Todo(taskDescription);
                taskCount ++;
                System.out.println("    ____________________________________________________________");
                System.out.println("     Mortis notes your tasks:");
                System.out.println("       " + tasks[taskCount - 1].toString());
                System.out.println("     Now you have " + taskCount + " tasks in the list.");
                System.out.println("    ____________________________________________________________");
            } else if (input.startsWith("deadline")) {
                String[] parts = input.split(" /by ");
                String taskDescription = parts[0].substring(9); // Extract description
                String deadline = parts[1]; // Extract deadline time
                tasks[taskCount] = new Deadline(taskDescription, deadline);
                taskCount++;
                System.out.println("    ____________________________________________________________");
                System.out.println("     Mortis notes your tasks:");
                System.out.println("       " + tasks[taskCount - 1].toString());
                System.out.println("     Now you have " + taskCount + " tasks in the list.");
                System.out.println("    ____________________________________________________________");
            } else if (input.startsWith("event")) {
                String[] parts = input.split(" /from ");
                String taskDescription = parts[0].substring(6); // Extract description
                String[] timeParts = parts[1].split(" /to");
                String fromTime = timeParts[0]; // Extract start time
                String toTime = timeParts[1]; // Extract end time
                tasks[taskCount] = new Event(taskDescription, fromTime, toTime);
                taskCount++;
                System.out.println("    ____________________________________________________________");
                System.out.println("     Mortis notes your tasks:");
                System.out.println("       " + tasks[taskCount - 1].toString());
                System.out.println("     Now you have " + taskCount + " tasks in the list.");
                System.out.println("    ____________________________________________________________");
            }
        }

        sc.close();
    }
}
