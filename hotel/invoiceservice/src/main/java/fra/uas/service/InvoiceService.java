package fra.uas.service;

import fra.uas.repository.BookingRepository;
import fra.uas.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fra.uas.dto.InvoiceDTO;
import fra.uas.model.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);





    public InvoiceDTO generateInvoice(int bookingId, String username, Hotel hotelDto) {
        //TODO userbooking anstelle von Bookingrepository
var bookings = hotelDto.getBookings();

var rooms = hotelDto.getRooms();

Booking userBooking = bookings.stream().filter(booking -> booking.getID() == bookingId).findFirst().orElse(null);

        if (userBooking == null) {
            logger.info("Booking not found");
            return null;
        }

        // Calculate the number of days stayed
        long daysStayed = (userBooking.getCheckOutDate().getTime() - userBooking.getCheckInDate().getTime()) / (1000 * 60 * 60 * 24);

        // Calculate the total cost
        double totalCost = daysStayed * calculatePricePerNight(userBooking, rooms);

        return createInvoiceDTO(userBooking, totalCost);
    }

    private double calculatePricePerNight(Booking booking, List<Room> rooms) {
        for (Room room : rooms) {
            if (room.getType().equals(booking.getRoomType())) {
                return room.getPricePerNight();
            }
        }
        return 0.0; // Standardwert, falls kein Zimmer gefunden wird
    }

    private InvoiceDTO createInvoiceDTO(Booking booking, double totalCost) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setBookingId(booking.getID());
        invoice.setRoomType(booking.getRoomType());
        invoice.setCheckInDate(dateFormat.format(booking.getCheckInDate()));
        invoice.setCheckOutDate(dateFormat.format(booking.getCheckOutDate()));
        invoice.setTotalCost(totalCost);
        return invoice;
    }

}