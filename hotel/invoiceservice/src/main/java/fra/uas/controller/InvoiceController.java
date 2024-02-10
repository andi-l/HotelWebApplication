package fra.uas.controller;

import fra.uas.dto.InvoiceDTO;
import fra.uas.model.Booking;
import fra.uas.model.Hotel;
import fra.uas.model.Room;
import fra.uas.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoiceservice/{bookingId}/{username}")
    public ResponseEntity<?> generateInvoice(@PathVariable int bookingId, @PathVariable String username, @RequestBody Hotel hotelDto) {
        InvoiceDTO invoice = invoiceService.generateInvoice(bookingId, username, hotelDto);
        if (invoice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Buchung nicht gefunden oder Fehler bei der Erstellung der Rechnung.");
        }
        return ResponseEntity.ok("Username : " + username + "\n" + invoice);
    }
}

