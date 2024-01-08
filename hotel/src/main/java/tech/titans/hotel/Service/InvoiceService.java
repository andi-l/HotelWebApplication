package tech.titans.hotel.Service;

import tech.titans.hotel.Model.Booking;
import tech.titans.hotel.Repository.BookingRepository;
import tech.titans.hotel.Repository.HotelRepository;
import tech.titans.hotel.Model.Hotel;
import tech.titans.hotel.Model.Room;

import java.text.SimpleDateFormat;
import java.util.Optional;

public class InvoiceService {

    private BookingRepository bookingRepository;
    private HotelRepository hotelRepository;

    public InvoiceService(BookingRepository bookingRepository, HotelRepository hotelRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
    }

    public void generateInvoice(int bookingId) {
        Optional<Booking> bookingOptional = findBookingById(bookingId);

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            long daysStayed = (booking.getCheckOutDate().getTime() - booking.getCheckInDate().getTime()) / (1000 * 60 * 60 * 24);
            double totalCost = daysStayed * calculatePricePerNight(booking);

            printInvoiceDetails(booking, totalCost);
        } else {
            System.out.println("Buchung nicht gefunden.");
        }
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

    private void printInvoiceDetails(Booking booking, double totalCost) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Rechnung für Buchung ID: " + booking.getID());
        System.out.println("Zimmertyp: " + booking.getRoomType());
        System.out.println("Check-In Datum: " + dateFormat.format(booking.getCheckInDate()));
        System.out.println("Check-Out Datum: " + dateFormat.format(booking.getCheckOutDate()));
        System.out.println("Gesamtkosten: " + totalCost + " EUR");
    }
}
