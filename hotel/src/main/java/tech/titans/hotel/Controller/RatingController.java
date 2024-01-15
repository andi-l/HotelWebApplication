package tech.titans.hotel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.titans.hotel.Model.Rating;
import tech.titans.hotel.Service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRating(@RequestBody Rating rating) {
        try {
            ratingService.addRating(rating);
            return ResponseEntity.ok("Rating added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding rating");
        }
    }

    @PostMapping("/average")
    public ResponseEntity<String> getAverage() {
        try {
            ratingService.getAverage();
            return ResponseEntity.ok("Average rating retrieved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving average rating");
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
}






