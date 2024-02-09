package fra.uas.config;

import fra.uas.repository.BookingRepository;
import fra.uas.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fra.uas.model.Hotel;
import fra.uas.model.Room;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import fra.uas.service.BookingService;
import fra.uas.service.InvoiceService;

@Component
public class InitData {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingRepository bookingRepository;

    private InvoiceService invoiceService;

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

        // Create Room 3 for Hotel 1
        Room room3 = new Room("Premium Double", 250.0, 2);
        hotel1.getRooms().add(room3);

        // Create Room 4 for Hotel 1
        Room room4 = new Room("Premium Double", 250.0, 2);
        hotel1.getRooms().add(room4);

        // Create Room 1 for Hotel 1
        Room room5 = new Room("Standard", 100.0, 1);
        hotel1.getRooms().add(room5);

        Room room6 = new Room("Standard", 100.0, 1);
        hotel1.getRooms().add(room6);

        Room room7 = new Room("Standard", 100.0, 1);
        hotel1.getRooms().add(room7);

        Room room8 = new Room("Standard", 100.0, 1);
        hotel1.getRooms().add(room8);

        // Add Hotel 1 to the repository
        hotelRepository.addHotel(hotel1);
    }
}
