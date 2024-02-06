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

//    @Override
//    public void addRating(Review review) {
//        ratingRepository.reviewArrayList.add(review);
//        System.out.println("Neue Bewertung " + review.getStars() + " erstellt!");
//        if (review.getComment() != null && !review.getComment().isEmpty()) {
//            System.out.println("Kommentar: " + review.getComment());
//        }
//    }


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



    // Alt
   // @Override
//   public double getAverage() {
//       if (ratingRepository.reviewArrayList.isEmpty()) {
//           System.out.println("Keine Bewertungen vorhanden.");
//           return 0;
//       }
//
//       int sum = 0;
//       for (Review review : ratingRepository.reviewArrayList) {
//           sum += review.getStars();
//       }
//
//       return (double) sum / ratingRepository.reviewArrayList.size();
//   }

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










//    public void addReviewId(Review review) {
//        int reviewId = Booking.getID();
//
//        reviewHashMap.put(reviewId, review);
//    }








   // Kann wahrscheinlich weg


//    public void leaveComment(int starsRating, String comment) {
//        if (starsRating >= 0 && starsRating < ratingRepository.reviewArrayList.size()) {
//            Review review = ratingRepository.reviewArrayList.get(starsRating);
//            System.out.println("Kommentar hinzugefügt zur Bewertung mit " + review.getStars() + " Sternen: " + comment);
//            review.setComment(comment);
//        } else {
//            System.out.println("Ungültiger Index für die Bewertung.");
//        }
//    }
}