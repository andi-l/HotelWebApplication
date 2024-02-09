package fra.uas.Controller;

import fra.uas.Model.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import fra.uas.Model.Review;
import fra.uas.Service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Optional;


@RestController
public class RatingController {

    @Autowired
    public RatingService ratingService;

    @PostMapping("/rating")
    public ResponseEntity<?> addRating(@RequestBody RatingDTO ratingDTO) {
        try {

            int stars = ratingDTO.getReview().getStars();
            if (stars < 0 || stars > 5) {
                return ResponseEntity.badRequest().body("Die Sternebewertung muss zwischen 0 und 5 liegen.");
            }

            // Add the rating using the RatingService
            ratingService.addRating(ratingDTO.getBooking(), ratingDTO.getReview());


            return ResponseEntity.ok("Rating added successfully " + " Buchung: " + ratingDTO.getBooking() + " Rating: " + ratingDTO.getReview());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding rating: " + e.getMessage());
        }
    }

    @GetMapping("/rating")
    public ResponseEntity<Collection<Review>> getAllRatings() {
        Collection<Review> reviews = ratingService.getAllRatings();
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/average")
    public ResponseEntity<?> getAverageRating() {
        try {
            double averageRating = ratingService.getAverage();

            // If there are no reviews
            if (averageRating == 0.0) {
                return ResponseEntity.ok("Keine Bewertungen vorhanden.");
            }

            return ResponseEntity.ok("Durchschnittliche Bewertung: " + averageRating);
        } catch (Exception e) {
            // If an error occurs, return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Abrufen der durchschnittlichen Bewertung: " + e.getMessage());
        }


    }


//    @DeleteMapping("/{reviewId}")
//public ResponseEntity<?> deleteRating(@PathVariable Long reviewId) {
//    try {
//        Optional<Review> existingReview = ratingService.getReviewById(reviewId);
//        if (existingReview.isPresent()) {
//            ratingService.deleteReview(reviewId);
//            return ResponseEntity.ok("Rating with ID " + reviewId + " deleted successfully");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting rating: " + e.getMessage());
//    }
//}

}
