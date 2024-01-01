package tech.titans.hotel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.titans.hotel.Service.BookingService;
import tech.titans.hotel.Model.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class Controller {

    @Autowired
    private BookingService bookingService;

    @GetMapping(value = "/availability/{checkInDate}/{checkOutDate}/{capacity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAvailability(
            @PathVariable("checkInDate") String checkInDateString,
            @PathVariable("checkOutDate") String checkOutDateString,
            @PathVariable("capacity") int capacity) {
        
        List<Room> availableRooms = bookingService.checkAvailability(checkInDateString, checkOutDateString, capacity);

        if (availableRooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(availableRooms);
        }
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBooking(
            @RequestParam("roomType") String roomType,
            @RequestParam("checkInDate") String checkInDateString,
            @RequestParam("checkOutDate") String checkOutDateString,
            @RequestParam("hotelName") String hotelName,
            @RequestParam("capacity") int capacity) {
        
        try {
            Booking newBooking = bookingService.createBooking(roomType, checkInDateString, checkOutDateString, hotelName, capacity);

            if (newBooking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kein Zimmer verfügbar für die angegebenen Daten und Kriterien.");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fehler bei der Erstellung der Buchung: " + e.getMessage());
        }
    }
}
