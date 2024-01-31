package fra.uas.Service;

import fra.uas.Repositories.BookingRepository;
import fra.uas.Repositories.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fra.uas.DTO.InvoiceDTO;
import fra.uas.Repositories.*;
import fra.uas.Model.*;

import java.text.SimpleDateFormat;

@Service
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public InvoiceDTO generateInvoice(int bookingId, String username) {
        Booking booking = bookingRepository.getBookingByUsernameAndId(username, bookingId);
    
        if (booking == null) {
            logger.info("Booking not found");
            return null;
        }
    
        // Calculate the number of days stayed
        long daysStayed = (booking.getCheckOutDate().getTime() - booking.getCheckInDate().getTime()) / (1000 * 60 * 60 * 24);
        
        // Calculate the total cost
        double totalCost = daysStayed * calculatePricePerNight(booking);
    
        return createInvoiceDTO(booking, totalCost);
    }
    

    private double calculatePricePerNight(Booking booking) {
        for (Hotel hotel : hotelRepository.hotelList) {
            for (Room room : hotel.getRooms()) {
                if (room.getType().equals(booking.getRoomType())) {
                    return room.getPricePerNight();
                }
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