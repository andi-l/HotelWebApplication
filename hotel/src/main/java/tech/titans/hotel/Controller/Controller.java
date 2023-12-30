package tech.titans.hotel.Controller;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.titans.hotel.Service.*;

import tech.titans.hotel.Model.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class Controller {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = "/availability/{checkInDate}/{checkOutDate}/{capacity}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
}