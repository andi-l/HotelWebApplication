package fra.uas.service;

import fra.uas.model.Booking;
import fra.uas.service.RatingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fra.uas.repository.RatingRepository;
import fra.uas.model.Review;

import java.util.ArrayList;
import java.util.HashMap;


@Service
public class RatingService implements RatingServiceInterface {


    @Autowired
    public RatingRepository ratingRepository;


    //Neu mit HashMap
    @Override
    public void addRating(Review review) {
        int ReviewId = Booking.getCounter();
        ratingRepository.reviewHashMap.put(ReviewId, review);
        System.out.println("Neue Bewertung " + review.getStars() + " erstellt!");
        if (review.getComment() != null && !review.getComment().isEmpty()) {
            System.out.println("Kommentar: " + review.getComment());
        }
    }

    //Neu mit Hashmap

    public double getAverage() {
        if (ratingRepository.reviewHashMap.isEmpty()) {
            System.out.println("Keine Bewertungen vorhanden.");
            return 0;
        }

        int sum = 0;
        ArrayList<Integer> allStars = new ArrayList<>();
        for (Review review : ratingRepository.reviewHashMap.values()) {
            int stars = review.getStars();
            allStars.add(stars);

            sum += stars;
        }

        return (double) sum / allStars.size();
    }

    public void deleteReview(Long reviewId) {
        ratingRepository.reviewHashMap.remove(reviewId);
        System.out.println("Bewertung mit ID " + reviewId + " gelöscht.");
    }
}