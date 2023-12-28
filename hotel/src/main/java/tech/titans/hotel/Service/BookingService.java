package tech.titans.hotel.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingService {

    public List<Room> checkAvailability(Date checkInDate, Date checkOutDate, String roomType) {
        List<Room> availableRooms = new ArrayList<>();

        // check availability and return available rooms
        for (Hotel hotel : hotelRepository.getHotels()) {
            for (Room room : hotel.getAvailablerooms()) {
                // Check if the room matches & free on specific date
                if (room.getType().equals(roomType) && isRoomAvailable(room, checkInDate, checkOutDate)) {
                    availableRooms.add(room);
                }
            }
        }

        return availableRooms;
    }

    // if a room is available within the specified dates
    private boolean isRoomAvailable(Room room, Date checkInDate, Date checkOutDate) {

        for (Booking booking : room.getBookingSchedule()) {
            // Check overlap with the specified dates
            if (booking.getCheckInDate().after(checkOutDate) || booking.getCheckOutDate().before(checkInDate)) {
                continue;
            } else {
                return false; // Room available
            }
        }
        return true; // Room not available
    }
}