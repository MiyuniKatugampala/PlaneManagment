import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class PlaneMangement {
    //2D array to represent seat availability(0:available, 1:not available )
    static int[][] seats = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };

    // bought tickets array
    static Ticket[] tickets = new Ticket[60];

    // total number of tickets bought
    static int total_tickets = 0;

    public static void main(String[]args){
        Scanner scanner = new Scanner(System.in);

        // Variable to store the user's menu option
        int option = 0;

        do {
            // Display the menu
            System.out.println("\n");
            System.out.println("‘Welcome to the Plane Management application");
            System.out.println("\n");
            System.out.println("**************************************************");
            System.out.println("MENU OPTIONS");
            System.out.println("**************************************************");
            System.out.println("1) Buy a seat");
            System.out.println("2) Cancel a seat");
            System.out.println("3) Find first available seat");
            System.out.println("4) Show seating plan");
            System.out.println("5) Print tickets information and total sales");
            System.out.println("6) Search ticket");
            System.out.println("0) Quit");
            System.out.println("**************************************************");

            try{
                System.out.print("\nPlease select an option: ");

                // Read user's option
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    case 0:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid option. Please select a valid option.");
                        break;
                }
            } catch
            (Exception e) {
                System.out.println(e);
                scanner.nextLine();
            }

        } while (option != 0);

        scanner.close();
    }
    public static void buy_seat() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\nBUYING SEATS");

            // get user inputs
            System.out.print("Enter row letter (A, B, C, D ): ");
            String rowLetter = scanner.nextLine().toUpperCase();

            int rowIndex = 0;
            switch (rowLetter) {
                case "A":
                    rowIndex = 0;
                    break;
                case "B":
                    rowIndex = 1;
                    break;
                case "C":
                    rowIndex = 2;
                    break;
                case "D":
                    rowIndex = 3;
                    break;
                default:
                    System.out.println("Invalid row letter.");
                    return;
            }

            System.out.print("Enter the seat number: ");
            int seatNumber = scanner.nextInt();
            int seatIndex = seatNumber - 1;

            // check if the seat is available and update the arrays
            if (seats[rowIndex][seatIndex] == 0) {
                seats[rowIndex][seatIndex] = 1;

                System.out.println("Enter passenger information");

                System.out.print("Name: ");
                String name = scanner.next();

                System.out.print("Surname: ");
                String surname = scanner.next();

                System.out.print("Email: ");
                String email = scanner.next();

                Person user = new Person(name, surname, email);

                double ticketPrice = 0;

                if (seatIndex < 5){
                    ticketPrice = 200.0;
                }else if(seatIndex < 9){
                    ticketPrice = 150.0;
                }else{
                    ticketPrice = 180.0;
                }

                Ticket newTicket = new Ticket(String.valueOf((char)(rowIndex + 'A')), seatNumber, ticketPrice, user);

                tickets[total_tickets++] = newTicket;

                // save data as a text file
                save(newTicket);

                System.out.println("Your purchase is successfully.");
            }
            else{
                System.out.println("This seat is unavailable. Please book another seat.");
            }
        }
        catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        finally {
            scanner.nextLine();
        }
    }
    public static void cancel_seat(){
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("\nCANCELLING SEATS");

            // get user inputs
            System.out.print("Enter row letter (A, B, C, D ): ");
            String rowLetter = scanner.nextLine().toUpperCase();

            int rowIndex = 0;
            switch (rowLetter) {
                case "A":
                    rowIndex = 0;
                    break;
                case "B":
                    rowIndex = 1;
                    break;
                case "C":
                    rowIndex = 2;
                    break;
                case "D":
                    rowIndex = 3;
                    break;
                default:
                    System.out.println("Invalid row letter.");
                    return;
            }

            System.out.print("Enter the seat number: ");
            int seatNumber = scanner.nextInt();
            int seatIndex = seatNumber - 1;

            if (seats[rowIndex][seatIndex] == 1) {
                // The seat was sold, now find the corresponding ticket to cancel
                for (int i = 0; i < total_tickets; i++){
                    Ticket ticket = tickets[i];
                    if (ticket == null){
                        continue;
                    }
                    if ((Objects.equals(ticket.getRow(), rowLetter)) && (ticket.getSeat() == seatNumber)){
                        // Cancel the ticket by setting its array position to null
                        tickets[i] = null;

                        // Shift the subsequent tickets up to fill the gap
                        System.arraycopy(tickets, i + 1, tickets, i, total_tickets - i - 1);
                        tickets[total_tickets - 1] = null; // Nullify the duplicate last element after shifting
                        total_tickets--; // Decrement ticketCount since we've effectively removed a ticket

                        // Mark the seat as available again
                        seats[rowIndex][seatIndex] = 0;

                        System.out.println("Ticket canceled successfully.");
                        return;
                    }
                }
                System.out.println("Ticket not found.");
            }
        }
        catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        finally {
            scanner.nextLine();
        }
    }
    public static void find_first_available(){
        // find the first element in seats and display seat information
        System.out.println("Finding the first available seat");
        for (int i = 0; i < seats.length; i++){
            for(int j = 0; j < seats[i].length; j++){
                if(seats[i][j] == 0){
                    System.out.println("First available seat: Row " + (char) (i+'A') + ", seats " +(j+1));
                    return;
                }
            }
        }
        System.out.println("Seats are occupied. No available seats found.");
    }
    public static void show_seating_plan(){
        System.out.println("\nSEATING PLAN");
        System.out.println();

        // loop through the array to display seating order
        for (int i = 0; i < seats.length; i++){
            for (int j = 0; j <seats[i].length; j++){
                if (seats[i][j] == 0) {
                    System.out.print("O ");
                }
                else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }

    }
    public static void print_tickets_info(){
        double total_price = 0;
        // tickets list
        StringBuilder list = new StringBuilder();

        // loop through tickets array
        for (Ticket ticket : tickets){
            if (ticket == null){
                break;
            }

            // replace row with letter
            String row = ticket.getRow();

            // create row list
            list = new StringBuilder(list + row + ticket.getSeat() + " ");

            // calculate total sales
            total_price = total_price + ticket.getPrice();
        }

        String output = String.format("""
                Ticket list: %s
                Total Sales: %.2f
                """, list, total_price);

        System.out.println(output);
    }
    public static void search_ticket(){
        Scanner input = new Scanner(System.in); // Creating an object to read inputs for buying seats
        System.out.print("Enter the row letter : "); // Getting the row letter
        String row = input.next();
        row = row.toUpperCase();

        System.out.print("Enter the seat number: "); // Asking for the seat number
        int seat = input.nextInt();

        for (Ticket ticket : tickets){
            if (ticket == null){
                continue;
            }

            // replace row with letter
            String ticket_row = ticket.getRow();

            if ((Objects.equals(ticket_row, row)) && (seat == ticket.getSeat())) {
                Person person = ticket.getUser();
                String content = "Seat: " + ticket_row + ticket.getSeat() + "\nName: " + person.getName() + "\nE-Mail: " + person.getEmail();
                System.out.println(content);
                return;
            }
        }

        // check if the seat is valid or not
        if(row.equalsIgnoreCase("A") || row.equalsIgnoreCase("B") | row.equalsIgnoreCase("C") | row.equalsIgnoreCase("D")){
            System.out.println("Seat is available.");
        }else {
            System.out.println("Invalid row letter.");
        }
    }
    public static void save(Ticket ticket){
        String filename = "";
        String row = null;

        row = ticket.getRow();

        filename = row + ticket.getSeat() + ".txt";

        try {
            File file = new File("tickets/" +filename);
            if (file.exists()) {
                // Use PrintWriter for efficient file clearing
                PrintWriter writer = new PrintWriter(file);
                writer.close();
            } else {
                file.createNewFile();
            }

            // create ticket details
            Person person = ticket.getUser();
            String content = "Row: " + row + "\nSeat number: " + ticket.getSeat() + "\nName: " + person.getName() + "\nE-Mail: " + person.getEmail() + "\nPrice: " + ticket.getPrice();

            // write content to the file
            FileWriter fileWriter = new FileWriter("tickets/" +filename, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

