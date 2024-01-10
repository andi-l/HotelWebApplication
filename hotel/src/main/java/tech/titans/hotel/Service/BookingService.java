package tech.titans.hotel.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tech.titans.hotel.Model.*;
import tech.titans.hotel.Repository.*;

@Component
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private BookingRepository bookingRepository;

    
// ... andere Importe ...

    public List<Room> checkAvailability(String checkInDateString, String checkOutDateString, int capacity) {
    try {
        Date checkInDate = parseDate(checkInDateString);
        Date checkOutDate = parseDate(checkOutDateString);

        // Convert current dates to LocalDate for comparison
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate checkInLocalDate = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Check if Check-In is in the past
        if (checkInLocalDate.isBefore(today)) {
            logger.warn("Das Check-In-Datum liegt in der Vergangenheit: {}", checkInDateString);
            return new ArrayList<>();
        }

        List<Room> availableRooms = new ArrayList<>();

        // Checking availability
        for (Hotel hotel : hotelRepository.getAllHotels()) {
            for (Room room : hotel.getRooms()) {
                if (room.getCapacity() == capacity && room.isClean() && isRoomAvailable(capacity, checkInDate, checkOutDate)) {
                    availableRooms.add(room);
                    logger.info("Verfügbarer und sauberer Raum: {}", room);
                }
            }
        }

        return availableRooms;
    } catch (ParseException e) {
        logger.error("Fehler bei der Datumsumwandlung: {}", e.getMessage());
        return new ArrayList<>();
    }
}


    private boolean isRoomAvailable(int capacity, Date checkInDate, Date checkOutDate) {
        for (Hotel hotel : hotelRepository.getAllHotels()) {
            for (Booking booking : hotel.getBookings()) {
                if (booking.getRoomCapacity() == capacity) {
                    if (!(booking.getCheckInDate().after(checkOutDate) || booking.getCheckOutDate().before(checkInDate))) {
                        // Überschneidung gefunden, Raum ist nicht verfügbar
                        logger.info("Keine verfügbaren Räume in dem Zeitraum");
                        return false;
                    }
                }
            }
        }
        return true; // Keine Überschneidungen gefunden, Raum ist verfügbar
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.bookingList;
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
    
    public Booking createBooking(String roomType, String checkInDateString, String checkOutDateString, int capacity) {
        try {
            Date checkInDate = parseDate(checkInDateString);
            Date checkOutDate = parseDate(checkOutDateString);
    
            List<Room> availableRooms = checkAvailability(checkInDateString, checkOutDateString, capacity);
            //System.out.println(availableRooms);
            for (Room room : availableRooms) {
                if (room.getType().equals(roomType)) {
                    Booking newBooking = new Booking(roomType, checkInDate, checkOutDate, capacity);
    
                    // Add the booking to the hotel
                    bookingRepository.bookingList.add(newBooking);
    
                    // Mark room as notClean so its not available
                    room.setClean(false);
                    logger.info("Erfolgreiche Buchung: " + newBooking);
                    return newBooking;
                }
            }
            logger.warn("Keine Buchung möglich");
            return null;

        } catch (ParseException e) {
            logger.error("Fehler bei der Datumsumwandlung: " + e.getMessage());
            return null;
        }
    }
    
    @Scheduled(cron = "0 0 12 * * ?") // Reinigung jeden Tag um 12:00 Uhr
    public void cleanRooms() {
        // Heutiges Datum in UTC
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));
        Date todayDate = Date.from(today.atStartOfDay(ZoneId.of("UTC")).toInstant());
    
        for (Hotel hotel : hotelRepository.getAllHotels()) {
            for (Room room : hotel.getRooms()) {
                if (!isRoomBookedToday(hotel, room, todayDate)) {
                    room.setClean(true);
                    logger.info("Zimmer gereinigt: " + room);
                }
            }
        }
    }
    
    private boolean isRoomBookedToday(Hotel hotel, Room room, Date todayDate) {
        return hotel.getBookings().stream()
            .anyMatch(booking -> booking.getRoomType().equals(room.getType()) &&
                    booking.getCheckInDate().compareTo(todayDate) <= 0 &&
                    booking.getCheckOutDate().compareTo(todayDate) > 0);
    }
        

//auch hier keien 2 te Forschleige, da es keine 2 tes hotel gibt 
public void cleanRoom(String roomType) {
    for (Hotel hotel : hotelRepository.getAllHotels()) {
        for (Room room : hotel.getRooms()) {
            if (room.getType() == roomType) {
                room.setClean(true);
                logger.info("Zimmer gereinigt und wieder verfügbar: " + room);
                return;
            }
        }
    }
    logger.warn("Zimmer mit dem Typ" + roomType + " nicht gefunden für Reinigung.");
}

}
