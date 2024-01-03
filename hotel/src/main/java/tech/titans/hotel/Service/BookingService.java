package tech.titans.hotel.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.titans.hotel.Model.*;
import tech.titans.hotel.Repository.*;

@Component
public class BookingService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Room> checkAvailability(String checkInDateString, String checkOutDateString, int capacity) {
        try {
            Date checkInDate = parseDate(checkInDateString);
            Date checkOutDate = parseDate(checkOutDateString);

            List<Room> availableRooms = new ArrayList<>();

            // check availability and return available rooms
            for (Hotel hotel : getAllHotels()) {
                for (Room room : hotel.getRooms()) {
                    // Check if the room matches & free on specific date 
                    if (room.getCapacity() == capacity && isRoomAvailable(capacity, checkInDate, checkOutDate)) {
                        availableRooms.add(room);
                    }
                }
            }

            return availableRooms;
        } catch (ParseException e) {
            // Handle the exception if the date strings could not be parsed
            return new ArrayList<>();
        }
        }
    

    // checks if a room with a given capacity is available within the specified dates across all hotels
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

      private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
    
    public Booking createBooking(String roomType, String checkInDateString, String checkOutDateString, String hotelName, int capacity) {
        try {
            Date checkInDate = parseDate(checkInDateString);
            Date checkOutDate = parseDate(checkOutDateString);
    
            List<Room> availableRooms = checkAvailability(checkInDateString, checkOutDateString, capacity);
    
            for (Hotel hotel : getAllHotels()) {
                if (hotel.getName().equals(hotelName)) {
                    for (Room room : availableRooms) {
                        if (room.getType().equals(roomType) && room.getHotel().getName().equals(hotelName)) {
                            Booking newBooking = new Booking(roomType, checkInDate, checkOutDate, hotelName, capacity);
    
                            // Add the booking to the hotel
                            hotel.addBooking(newBooking);
    
                            // Remove the booked room from the hotel
                            hotel.removeRoom(room);
    
                            return newBooking;
                        }
                    }
                }
            }
    
            // Wenn kein Zimmer verfügbar ist oder das Hotel nicht gefunden wurde
            System.out.println("Kein Zimmer verfügbar für die angegebenen Daten und Kriterien im Hotel: " + hotelName);
        } catch (ParseException e) {
            System.out.println("Fehler bei der Datumsumwandlung: " + e.getMessage());
        }
        return null;
    }
          
    
    }

    


