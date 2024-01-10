package tech.titans.hotel.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.titans.hotel.DTO.InvoiceDTO;
import tech.titans.hotel.Repository.*;
import tech.titans.hotel.Model.*;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public InvoiceDTO generateInvoice(int bookingId) {
        Optional<Booking> bookingOptional = findBookingById(bookingId);

        if (!bookingOptional.isPresent()) {
            logger.info("Buchung nicht gefunden.");
            return null; // Oder eine geeignete Exception werfen
        }

        Booking booking = bookingOptional.get();
        long daysStayed = (booking.getCheckOutDate().getTime() - booking.getCheckInDate().getTime()) / (1000 * 60 * 60 * 24);
        double totalCost = daysStayed * calculatePricePerNight(booking);

        return createInvoiceDTO(booking, totalCost);
    }

    private Optional<Booking> findBookingById(int bookingId) {
        return bookingRepository.bookingList.stream()
                .filter(booking -> booking.getID() == bookingId)
                .findFirst();
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
