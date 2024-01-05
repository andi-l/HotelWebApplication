package tech.titans.hotel.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RatingController {
    
    private List<Integer> ratings = new ArrayList<>();

    @GetMapping("/bewertungen")
    @ResponseBody
    public List<Integer> getRatings() {
        return ratings;
    }

    @PostMapping("/bewertung")
    @ResponseBody
    public String addRating(@RequestParam int bewertung) {
        if (bewertung < 1 || bewertung > 5) {
            return "Ungültige Bewertung. Bitte geben Sie eine Bewertung zwischen 1 und 5 ein.";
        } else {
            ratings.add(bewertung);
            return "Bewertung hinzugefügt: " + bewertung;
        }
    }

    @GetMapping("/durchschnitt")
    @ResponseBody
    public String getAverageRating() {
        if (ratings.isEmpty()) {
            return "Es liegen keine Bewertungen vor.";
        } else {
            double sum = 0;
            for (int rating : ratings) {
                sum += rating;
            }
            double average = sum / ratings.size();
            return "Durchschnittliche Bewertung: " + average;
        }
    }
}



