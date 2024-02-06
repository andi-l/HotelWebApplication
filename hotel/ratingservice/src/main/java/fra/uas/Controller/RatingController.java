package fra.uas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import fra.uas.model.Review;
import fra.uas.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;


@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    public RatingService ratingService;

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody Review review) {
        try {
            if (review.getStars() == null) {
                return ResponseEntity.badRequest().body("You have to put a star rating");
            }

            System.out.println(review);
            return ResponseEntity.ok("Rating added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding rating: " + e.getMessage());
        }
    }



    @GetMapping("/average")
    public ResponseEntity<?> getAverage() {
        try {
            double average = ratingService.getAverage();
            return ResponseEntity.ok("Average rating: " + average + " retrieved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving average rating: " + e.getMessage());
        }
    }

    @DeleteMapping("/{reviewId}")
public ResponseEntity<?> deleteRating(@PathVariable Long reviewId) {
    try {
        Optional<Review> existingReview = ratingService.getReviewById(reviewId);
        if (existingReview.isPresent()) {
            ratingService.deleteReview(reviewId);
            return ResponseEntity.ok("Rating with ID " + reviewId + " deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting rating: " + e.getMessage());
    }
}

}