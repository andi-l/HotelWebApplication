package tech.titans.hotel.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.titans.hotel.Model.Hotel;
import tech.titans.hotel.Model.Room;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
public class InitData {

    @Autowired
    private HotelRepository hotelRepository;

    @PostConstruct
    public void init() {
        // Create Hotel 1
        Hotel hotel1 = new Hotel("Hotel A", "Location A", 4.5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Create Room 1 for Hotel 1
        Room room1 = new Room("Single", 100.0, 1);
        hotel1.getAvailablerooms().add(room1);

        // Create Room 2 for Hotel 1
        Room room2 = new Room("Double", 150.0, 2);
        hotel1.getAvailablerooms().add(room2);

        // Add Hotel 1 to the repository
        hotelRepository.add(hotel1);

        // Print the hotel entries
        System.out.println(hotel1.toString());
    }
}