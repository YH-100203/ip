import java.util.Scanner;

public class Mortis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Greetings, mortal. I am Mortis, your eternal assistant.");
        System.out.println(" What dark secret may I help you uncover today?");
        System.out.println("____________________________________________________________");


        // Echo loop
        while (true) {
            // Standard code for input
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("    ____________________________________________________________");
                System.out.println("     Farewell, traveler. Mortis shall await your return...");
                System.out.println("    ____________________________________________________________");
                break;
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("     Mortis repeats your words: " + input); // To echo input
                System.out.println("    ____________________________________________________________");
            }
        }

        sc.close();
    }
}
