package fra.uas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fra.uas.repository.RatingRepository;
import fra.uas.model.Review;

@Service
public class RatingService implements RatingServiceInterface {


    @Autowired
    public RatingRepository ratingRepository;

    @Override
    public void addRating(Review review) {
        ratingRepository.reviewArrayList.add(review);
        System.out.println("Neue Bewertung " + review.getStars() + " erstellt!");
        if (review.getComment() != null && !review.getComment().isEmpty()) {
            System.out.println("Kommentar: " + review.getComment());
        }
    }


   // @Override
   public double getAverage() {
       if (ratingRepository.reviewArrayList.isEmpty()) {
           System.out.println("Keine Bewertungen vorhanden.");
           return 0;
       }

       int sum = 0;
       for (Review review : ratingRepository.reviewArrayList) {
           sum += review.getStars();
       }

       return (double) sum / ratingRepository.reviewArrayList.size();
   }


    public void leaveComment(int starsRating, String comment) {
        if (starsRating >= 0 && starsRating < ratingRepository.reviewArrayList.size()) {
            Review review = ratingRepository.reviewArrayList.get(starsRating);
            System.out.println("Kommentar hinzugefügt zur Bewertung mit " + review.getStars() + " Sternen: " + comment);
            review.setComment(comment);
        } else {
            System.out.println("Ungültiger Index für die Bewertung.");
        }
    }
}