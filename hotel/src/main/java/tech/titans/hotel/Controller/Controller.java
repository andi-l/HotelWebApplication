package tech.titans.hotel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/availability/{checkInDate}/{checkOutDate}/{roomType}")
    public ResponseEntity<List<Room>> checkAvailability(
            @PathVariable("checkInDate") Date checkInDate,
            @PathVariable("checkOutDate") Date checkOutDate,
            @PathVariable("roomType") String roomType) {

        List<Room> availableRooms = bookingService.checkAvailability(checkInDate, checkOutDate, roomType);

        if (availableRooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(availableRooms);
        }
}