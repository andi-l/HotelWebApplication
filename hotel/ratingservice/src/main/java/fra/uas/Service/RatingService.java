package fra.uas.service;

import fra.uas.model.Booking;
import fra.uas.model.*;
import fra.uas.service.RatingServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fra.uas.repository.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


@Service
public class RatingService implements RatingServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    @Autowired
    public RatingRepository ratingRepository;

    //HashMap
    @Override
    public void addRating(Booking booking, Review review) {
        // Check that the Booking and Review objects are not null
        if (booking == null || review == null) {
            logger.warn("Review oder Booking ist null, kann nicht zum Repository hinzugefügt werden.");
            return;
        } else {
            ratingRepository.addReview(booking, review);
            logger.info("Review erfolgreich für Booking hinzugefügt: " + booking.toString());
        }
    }

    public Collection<Review> getAllRatings() {
        return ratingRepository.getAllReviews();
    }

    public double getAverage() {
        // Check if there are available Reviews
        if (ratingRepository.reviewHashMap.isEmpty()) {
            logger.warn("Keine Bewertungen vorhanden.");
            return 0.0; // Return 0 if there are no reviews
        }

        int sum = 0; // Total star Ratings
        int count = 0; // Number of Reviews

        // Iterate over all reviews in the repository
        for (Review review : ratingRepository.reviewHashMap.values()) {
            if (review != null && review.getStars() != null) { // Make sure review and stars are not zero
                sum += review.getStars(); // Adding the stars to the total
                count++; // Increase the counter
            }
        }

        if (count == 0) {
            logger.warn("Keine gültigen Bewertungen vorhanden.");
            return 0.0; // Returns 0 if there are no valid reviews
        }

        // Calculating the average and returning it
        return (double) sum / count;
    }


    public void deleteReview(Long reviewId) {
        ratingRepository.reviewHashMap.remove(reviewId);
        System.out.println("Bewertung mit ID " + reviewId + " gelöscht.");
    }
}