import java.util.Scanner;

public class Mortis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Greetings, mortal. I am Mortis, your eternal assistant.");
        System.out.println(" What dark secret may I help you uncover today?");
        System.out.println("____________________________________________________________");

        // Task list to store user input
        String[] tasks = new String[100]; // Task list to store user input
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
                    System.out.println("     " + (i+1) + ". " + tasks[i]);
                }
                System.out.println("    ____________________________________________________________");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("     Mortis notes your tasks: " + input); // To echo input
                System.out.println("    ____________________________________________________________");
                tasks[taskCount] = input;
                taskCount ++;
            }
        }

        sc.close();
    }
}
