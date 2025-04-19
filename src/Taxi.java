import java.util.*;

public class Taxi {
    int id;
    char currentPoint = 'A';
    int totalEarnings = 0;
    List<Booking> bookings = new ArrayList<>();

    public Taxi(int id) {
        this.id = id;
    }

    public boolean isAvailable(int requestTime) {
        if (bookings.isEmpty()) return true; // No booking then return true
        Booking lastBooking = bookings.get(bookings.size() - 1);
        return lastBooking.dropTime <= requestTime; // Check the lastBooking dropTime is <= request time
    }

    public int calculateEarnings(char from , char to) {
        int distance = Math.abs(to - from) * 15; // Kilometre
        return 100 + Math.max(0, (distance - 5) * 10); // First 5 kilometre = 100 + remaining kilometre Amount
    }

    public void addBooking(Booking booking) {
        bookings.add(booking); // add the booking
        totalEarnings += booking.amount; // update the total earnings
        currentPoint = booking.to; // Update the current point
    }
}