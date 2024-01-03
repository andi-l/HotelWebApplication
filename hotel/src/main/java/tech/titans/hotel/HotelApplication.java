package tech.titans.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import tech.titans.hotel.Model.Booking;
import tech.titans.hotel.Model.Hotel;
import tech.titans.hotel.Service.BookingService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class HotelApplication {

    public static void main(String[] args) {
        // Starten der Spring Boot-Anwendung und Abrufen des Anwendungskontexts
        ConfigurableApplicationContext context = SpringApplication.run(HotelApplication.class, args);

        // Abrufen des BookingService aus dem Spring-Anwendungskontext
        BookingService bookingService = context.getBean(BookingService.class);

        // Verwenden des BookingService, um eine Buchung zu erstellen
        Booking booking = bookingService.createBooking("Standard ", "2024-04-10", "2024-04-15", "hotel1", 1);
        if (booking != null) {
            System.out.println("Buchung erfolgreich erstellt: " + booking);
        } else {
            System.out.println("Buchung konnte nicht erstellt werden.");
        }
    }
}
