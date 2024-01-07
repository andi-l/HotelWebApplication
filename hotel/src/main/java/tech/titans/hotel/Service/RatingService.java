package tech.titans.hotel.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.titans.hotel.Repository.RatingRepository;
import tech.titans.hotel.Model.Rating;

@Service
public class RatingService implements RatingServiceInterface {


    @Autowired
    public RatingRepository ratingRepository;

    @Override
    public void addRating(Rating rating) {
        ratingRepository.ratingArrayList.add(rating);
        System.out.println("Neue Bewertung " + rating.getStars() + " erstellt!");
        if (rating.getComment() != null && !rating.getComment().isEmpty()) {
            System.out.println("Kommentar: " + rating.getComment());
        }
    }


   // @Override
    public void getAverage() {
        if (ratingRepository.ratingArrayList.isEmpty()) {
            System.out.println("Keine Bewertungen vorhanden.");
            return;
        }

        int sum = 0;
        for (Rating rating : ratingRepository.ratingArrayList) {
            sum += rating.getStars();
        }

        double durchschnitt = (double) sum / ratingRepository.ratingArrayList.size();
        System.out.println("Durchschnittliche Bewertung: " + durchschnitt);


    }

    public void leaveComment(int starsRating, String comment) {
        if (starsRating >= 0 && starsRating < ratingRepository.ratingArrayList.size()) {
            Rating rating = ratingRepository.ratingArrayList.get(starsRating);
            System.out.println("Kommentar hinzugefügt zur Bewertung mit " + rating.getStars() + " Sternen: " + comment);
            rating.setComment(comment);
        } else {
            System.out.println("Ungültiger Index für die Bewertung.");
        }
    }
}


