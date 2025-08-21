import java.util.Scanner;

public class Mortis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Mortis");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");


        // Echo loop
        while (true) {
            // Standard code for input
            String input = sc.nextLine();
            System.out.println("    ____________________________________________________________");
            System.out.println("     " + input); // To echo input
            System.out.println("    ____________________________________________________________");
            break;

        }

    }
}
