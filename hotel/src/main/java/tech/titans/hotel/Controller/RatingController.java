package tech.titans.hotel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.titans.hotel.Model.Review;
import tech.titans.hotel.Service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


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


    @PostMapping("/comment")
    public ResponseEntity<String> leaveComment(@RequestParam int starsRating, @RequestParam String comment) {
        try {
            ratingService.leaveComment(starsRating, comment);
            return ResponseEntity.ok("Comment added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding comment");
        }
    }
    @DeleteMapping("/comment/{reviewId}")
    public ResponseEntity<String> deleteComment(@PathVariable int reviewId) {
        try {
            boolean deleted = ratingService.deleteComment(reviewId);
            
            if (deleted) {
                return ResponseEntity.ok("Comment deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment");
        }

}
}





