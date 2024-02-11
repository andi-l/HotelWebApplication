package fra.uas.controller;

import fra.uas.dto.BookingDTO;
import fra.uas.model.Booking;
import fra.uas.model.Room;
import fra.uas.repository.BookingRepository;
import fra.uas.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    // Get all available rooms
    @GetMapping(value = "/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAvailability(
            @RequestParam("checkInDate") String checkInDateString,
            @RequestParam("checkOutDate") String checkOutDateString,
            @RequestParam("capacity") int capacity) {

        List<Room> availableRooms = bookingService.checkAvailability(checkInDateString, checkOutDateString, capacity);

        if (availableRooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rooms with matching criteria");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(availableRooms);
        }
    }

    // Create a new booking
    @PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingRequest) {
        try {
            Booking newBooking = bookingService.createBooking(
                    bookingRequest.getRoomType(),
                    bookingRequest.getCheckInDate(),
                    bookingRequest.getCheckOutDate(),
                    bookingRequest.getCapacity(),
                    bookingRequest.getUsername());
            if (newBooking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kein Zimmer verfügbar für die angegebenen Daten und Kriterien.");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body("Erfolgreiche Buchung:" + newBooking);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fehler bei der Erstellung der Buchung: " + e.getMessage());
        }
    }

    //Get all bookings for a user
    @GetMapping(value = "/booking/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBookings(@PathVariable String username) {
        List<Booking> bookings = bookingRepository.getAllBookingsByUsername(username);
        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found for user: " + username);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(bookings);
        }
    }

    //Get a booking by username and bookingId
    @GetMapping("/booking/{username}/{bookingId}")
    public ResponseEntity<?> getBookingByUsernameAndId(@PathVariable String username, @PathVariable int bookingId) {
        Booking booking = bookingRepository.getBookingByUsernameAndId(username, bookingId);
        if (booking == null) {
            return ResponseEntity.notFound().build(); // Buchung nicht gefunden
        }
        return ResponseEntity.status(HttpStatus.OK).
                body(booking);
    }

    @GetMapping("/hotel/rooms")
    public ResponseEntity<?> getAllRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllRooms());
    }
}



