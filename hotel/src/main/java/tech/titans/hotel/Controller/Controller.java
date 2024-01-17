package tech.titans.hotel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.titans.hotel.Service.*;
import tech.titans.hotel.DTO.*;
import tech.titans.hotel.Model.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private InvoiceService invoiceService;

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

    @PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingRequest) {
        
        try {
            Booking newBooking = bookingService.createBooking(
                    bookingRequest.getRoomType(), 
                    bookingRequest.getCheckInDate(), 
                    bookingRequest.getCheckOutDate(), 
                    bookingRequest.getCapacity());

            if (newBooking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kein Zimmer verfügbar für die angegebenen Daten und Kriterien.");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body("Erfolgreiche Buchung:" + newBooking);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fehler bei der Erstellung der Buchung: " + e.getMessage());
        }
    }


    @GetMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kein Zimmer verfügbar für die angegebenen Daten und Kriterien.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(bookings);
        }
    }

    @GetMapping("/booking/{id}/invoice")
    public ResponseEntity<?> generateInvoice(@PathVariable int bookingId) {
        InvoiceDTO invoice = invoiceService.generateInvoice(bookingId);
        if (invoice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Buchung nicht gefunden oder Fehler bei der Erstellung der Rechnung.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Rechnung" + invoice);
    }
}
