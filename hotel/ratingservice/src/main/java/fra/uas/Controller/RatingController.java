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

            // Füge die Bewertung mithilfe des RatingService hinzu
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

            // Wenn keine Bewertungen vorhanden sind
            if (averageRating == 0.0) {
                return ResponseEntity.ok("Keine Bewertungen vorhanden.");
            }

            return ResponseEntity.ok("Durchschnittliche Bewertung: " + averageRating);
        } catch (Exception e) {
            // Bei einem Fehler geben Sie eine entsprechende Fehlermeldung zurück
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Abrufen der durchschnittlichen Bewertung: " + e.getMessage());
        }


    }
}
