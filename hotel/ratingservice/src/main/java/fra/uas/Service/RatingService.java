package fra.uas.Service;

import fra.uas.Model.*;
import fra.uas.Service.RatingServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fra.uas.Repository.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


@Service
public class RatingService implements RatingServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    @Autowired
    public RatingRepository ratingRepository;

    //Neu mit HashMap
    @Override
    public void addRating(Booking booking, Review review) {
        // Überprüfen, ob die Booking- und Review-Objekte nicht null sind
        if (booking == null || review == null) {
            logger.warn("Review oder Booking ist null, kann nicht zum Repository hinzugefügt werden.");
            return;
        }
        else {
        ratingRepository.addReview(booking, review);
        logger.info("Review erfolgreich für Booking hinzugefügt: " + booking.toString());
        }
    }

    public Collection<Review> getAllRatings() {
        return ratingRepository.getAllReviews();
    }

    public double getAverage() {
        // Prüfen, ob Bewertungen vorhanden sind
        if (ratingRepository.reviewHashMap.isEmpty()) {
            logger.warn("Keine Bewertungen vorhanden.");
            return 0.0; // Rückgabe 0, wenn keine Bewertungen vorhanden sind
        }

        int sum = 0; // Summe der Sternebewertungen
        int count = 0; // Anzahl der Bewertungen

        // Iterieren über alle Bewertungen im Repository
        for (Review review : ratingRepository.reviewHashMap.values()) {
            if (review != null && review.getStars() != null) { // Sicherstellen, dass review und stars nicht null sind
                sum += review.getStars(); // Hinzufügen der Sterne zur Summe
                count++; // Erhöhen des Zählers
            }
        }

        if (count == 0) {
            logger.warn("Keine gültigen Bewertungen vorhanden.");
            return 0.0; // Rückgabe 0, wenn keine gültigen Bewertungen vorhanden sind
        }

        // Berechnung des Durchschnitts und Rückgabe
        return (double) sum / count;
    }


    public void deleteReview(Long reviewId) {
        ratingRepository.reviewHashMap.remove(reviewId);
        System.out.println("Bewertung mit ID " + reviewId + " gelöscht.");
    }
}