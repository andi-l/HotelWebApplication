package tech.titans.hotel.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.titans.hotel.Model.Booking;
import tech.titans.hotel.Model.Hotel;
import tech.titans.hotel.Model.Room;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import tech.titans.hotel.Repository.*;
import tech.titans.hotel.Service.BookingService;

@Component
public class InitData {

    @Autowired
    HotelRepository hotelList;

    @Autowired
    BookingService bookingService;
    
    @PostConstruct
    public void init() {

        

        // Create Hotel 1
        Hotel hotel1 = new Hotel("Hotel1", "Location A", 4.5, new ArrayList<>(), new ArrayList<>());

        // Create Room 1 for Hotel 1
        Room room1 = new Room("Standard", 100.0, 1);
        hotel1.getRooms().add(room1);

        // Create Room 2 for Hotel 1
        Room room2 = new Room("Standard Double", 150.0, 2);
        hotel1.getRooms().add(room2);

        // Create Room 2 for Hotel 1
        Room room3 = new Room("Premium Double", 250.0, 2);
        hotel1.getRooms().add(room3);

        // Add Hotel 1 to the repository
        hotelList.addHotel(hotel1);

        // Print the hotel entries
        System.out.println(hotel1.toString());

        // Verwenden des BookingService, um eine Buchung zu erstellen
        Booking booking = bookingService.createBooking("Standard ", "2024-04-10", "2024-04-15", 1);
        if (booking != null) {
            System.out.println("Buchung erfolgreich erstellt: " + booking);
        } else {
            System.out.println("Buchung konnte nicht erstellt werden.");
        }

    }
    }
