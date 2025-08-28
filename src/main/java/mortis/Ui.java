package mortis;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the interaction with the user via the console.
 * It displays messages to the user and reads input from the user.
 */
public class Ui {
    private final Scanner sc = new Scanner(System.in);

    /**
     * Displays a welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println(" Greetings, mortal. I am Mortis, your eternal assistant.");
        System.out.println(" What dark secret may I help you uncover today?");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a line separator.
     */
    public void showLine() {
        System.out.println("    ____________________________________________________________");
    }


    /**
     * Displays an error message if loading tasks fails.
     */
    public void showError(String message) {
        showLine();
        System.out.println("     " + message);
        showLine();
    }

    /**
     * Reads the command input by the user
     *
     * @return the full command entered by the user
     */
    public String readCommand() {
        return sc.nextLine();
    }

    public void showList(TaskList tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println("     Mortis has not yet received any tasks... *sadness*");
        } else {
            System.out.println("     Mortis’ records of your tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("     " + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    public void showAdd(Task added, int newCount) {
        showLine();
        System.out.println("     Mortis notes your tasks:");
        System.out.println("       " + added);
        System.out.println("     Now you have " + newCount + " tasks in the list.");
        showLine();
    }

    public void showDelete(Task deleted, int newCount) {
        showLine();
        System.out.println("     Noted. I've removed this task from the abyss");
        System.out.println("       " + deleted);
        System.out.println("     Now you have " + newCount + " tasks in the list.");
        showLine();
    }

    public void showMarked(Task t) {
        showLine();
        System.out.println("     Ah... the task is now done. The darkness has claimed it:");
        System.out.println("       " + t);
        showLine();
    }

    public void showUnmarked(Task t) {
        showLine();
        System.out.println("     OK... I've pulled the task back from the abyss. It is undone now:");
        System.out.println("       " + t);
        showLine();
    }

    public void showBye() {
        showLine();
        System.out.println("     Farewell, traveler. Mortis shall await your return...");
        showLine();
    }

    public void showFoundTasks(ArrayList<Task> matches) {
        showLine();
        if (matches.isEmpty()) {
            System.out.println("     No matching tasks found.");
        } else {
            System.out.println("     Here are the matching tasks found mortal:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println("     " + (i+1) + ". " + matches.get(i).toString());
            }
        }
        showLine();
    }
}

