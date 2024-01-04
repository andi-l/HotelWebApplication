package tech.titans.hotel.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.titans.hotel.Model.*;
import tech.titans.hotel.Repository.*;

@Component
public class BookingService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Room> checkAvailability(String checkInDateString, String checkOutDateString, int capacity) {
        try {
            Date checkInDate = parseDate(checkInDateString);
            Date checkOutDate = parseDate(checkOutDateString);

            List<Room> availableRooms = new ArrayList<>();

            // check availability and return available rooms
            for (Hotel hotel : getAllHotels()) {
                for (Room room : hotel.getRooms()) {
                    if (room.getCapacity() == capacity && isRoomAvailable(capacity, checkInDate, checkOutDate)) {
                        availableRooms.add(room);
                    }
                }
            }

            return availableRooms;
        } catch (ParseException e) {
            // Handle the exception if the date strings could not be parsed
            System.out.println("Fehler bei der Datumsumwandlung: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private boolean isRoomAvailable(int capacity, Date checkInDate, Date checkOutDate) {
        for (Hotel hotel : getAllHotels()) {
            for (Booking booking : hotel.getBookings()) {
                if (booking.getRoomCapacity() != capacity) {
                    continue; // Skip bookings for rooms with different capacity
                }

                // Check specific dates
                if (booking.getCheckInDate().after(checkOutDate) || booking.getCheckOutDate().before(checkInDate)) {
                    continue;
                } else {
                    return false; // No rooms available
                }
            }
        }
        return true; // Rooms available
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.hotelList;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.bookingList;
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
    
    public Booking createBooking(String roomType, String checkInDateString, String checkOutDateString, int capacity) {
        try {
            Date checkInDate = parseDate(checkInDateString);
            Date checkOutDate = parseDate(checkOutDateString);
    
            List<Room> availableRooms = checkAvailability(checkInDateString, checkOutDateString, capacity);
    
            for (Room room : availableRooms) {
                if (room.getType().equals(roomType)) {
                    Booking newBooking = new Booking(roomType, checkInDate, checkOutDate, capacity);
    
                    // Add the booking to the hotel
                    bookingRepository.bookingList.add(newBooking);
    
                    // Remove the booked room from the hotel
                    hotelRepository.hotelList.get(0).removeRoom(room);
    
                    return newBooking;
                }
            }

            // Wenn kein Zimmer verfügbar ist
            System.out.println("Kein Zimmer verfügbar für die angegebenen Daten und Kriterien");
            return null;

        } catch (ParseException e) {
            System.out.println("Fehler bei der Datumsumwandlung: " + e.getMessage());
            return null;
        }
    }
}
