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
}
