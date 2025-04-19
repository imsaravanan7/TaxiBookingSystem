import java.util.*;

public class TaxiBookingSystem {
    static List<Taxi> taxis = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static int customerCount = 1;

    public static void main(String[] args) {
        System.out.print("Enter the number of taxis : ");

        int numTaxis;
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            input.next(); // Consume invalid input
            System.out.print("Enter the number of taxis: ");
        }
        numTaxis = input.nextInt();
        initializeTaxis(numTaxis);

        try {
            while (true) { // Create infinite loop
                System.out.println("\n1. Book Taxi \n2. Display Taxi Details \n3. Exit\n");
                System.out.print("Enter your choice : ");

                if (!input.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    input.next(); // consume invalid input
                    continue;
                }
                int choice = input.nextInt();

                switch (choice) {
                    case 1 :
                        bookTaxi();
                        break;
                    case 2 :
                        displayTaxiDetails();
                        break;
                    case 3 :
                        System.out.println("Existing ...");
                        return;
                    default :
                        System.out.println("Invalid choice. Please try again ...");
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
    }

    // Initialize the taxis
    public static void initializeTaxis(int n) {
        for (int i=1; i <= n; i++) {
            taxis.add(new Taxi(i)); // Create n Taxi objects with IDs 1, 2 ... n.
        }
    }

    public static void bookTaxi() {
        int customerId = customerCount++; // 1
        char pickupPoint, dropPoint;

        // Check user is Enter a valid input
        while (true) {
            System.out.print("Enter Pickup Point (A-F) : ");
            String pickupInput = input.next().toUpperCase();
            if (pickupInput.length() == 1 && pickupInput.charAt(0) >= 'A' && pickupInput.charAt(0) <= 'F') {
                pickupPoint = pickupInput.charAt(0);
                break;
            } else {
                System.out.println("Invalid input! Please enter a single letter between A and F.");
            }
        }

        // Check user is Enter a valid input
        while (true) {
            System.out.print("Enter Drop Point (A-F) : ");
            String dropInput = input.next().toUpperCase();
            if (dropInput.length() == 1 && dropInput.charAt(0) >= 'A' && dropInput.charAt(0) <= 'F') {
                dropPoint = dropInput.charAt(0);
                break;
            } else {
                System.out.println("Invalid input! Please enter a single letter between A and F.");
            }
        }

        System.out.print("Enter Pickup Time (in hours & 24 hour format) : ");

        int pickupTime;
        // Check user is Enter a valid input
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            input.next(); // Consume invalid input
            System.out.print("Enter Pickup Time (in hours & 24 hour format) : ");
        }
        pickupTime = input.nextInt();

        Taxi selectedTaxi = null; // Initialize the Taxi
        int minDistance = Integer.MAX_VALUE; // Return the maximum value of int

        for (Taxi taxi : taxis) {
            // Check taxi is available at required pickup time
            if (taxi.isAvailable(pickupTime)) {
                // Distance b/w current location and pickup point
                int distance = Math.abs(taxi.currentPoint - pickupPoint);
                // Select the taxi based on min distance or low earnings if distance are equal
                if(distance < minDistance || (distance == minDistance && taxi.totalEarnings < selectedTaxi.totalEarnings)) {
                    selectedTaxi = taxi;
                    minDistance = distance;
                }
            }
        }

        if(selectedTaxi == null) {
            System.out.print("Booking rejected. No taxi available.");
            return;
        }

        int dropTime = pickupTime + Math.abs(dropPoint - pickupPoint);
        int amount = selectedTaxi.calculateEarnings(pickupPoint, dropPoint);
        int bookingId = selectedTaxi.bookings.size() + 1;

        Booking booking = new Booking(bookingId, customerId, pickupPoint, dropPoint, pickupTime, dropTime, amount);
        // Add the new booking to the selected taxi
        selectedTaxi.addBooking(booking);
        System.out.print("Tax-" + selectedTaxi.id + " is Allotted.\n");
    }

    public static void displayTaxiDetails() {
        for (Taxi taxi : taxis) {
            System.out.println("Taxi-" + taxi.id + " Total Earnings: Rs." + taxi.totalEarnings);
            System.out.printf("%-10s %-10s %-5s %-5s %-12s %-9s %-6s%n",
                    "BookingId", "CustomerId", "From", "To", "PickUpTime", "DropTime", "Amount");
            for(Booking booking : taxi.bookings) {
                System.out.printf("%-10d %-10d %-5c %-5c %-12d %-9d %-6d%n",
                        booking.bookingId, booking.customerId, booking.from, booking.to,
                        booking.pickupTime, booking.dropTime, booking.amount);
            }
            System.out.println();
        }
    }

}
