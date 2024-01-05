package tech.titans.hotel.Service;

import org.springframework.stereotype.Service;
//import tech.titans.hotel.Model.User;
//import tech.titans.hotel.Model.RatingRepository;
import tech.titans.hotel.Model.Rating;
import java.util.Scanner;
import java.util.ArrayList;


public class RatingService2 {
    
    static ArrayList<Integer> ratings = new ArrayList<Integer>();

    public static void main(String[] args) {

        evaluateHotel();
        getaverage();
        leavecomment();

    }

    static void evaluateHotel() {

       Scanner eingaben = new Scanner(System.in);
       System.out.println("Bitte geben Sie Ihre Sternebewertung (1-5) für das Hotel ein: ");
       int value = eingaben.nextInt();

       if (value < 1 || value > 5) {
           System.out.println("Ungültige Bewertung. Bitte geben Sie eine Bewertung zwischen 1 und 5 ein. ");
       } else {
           ratings.add(value);
           System.out.println(ratings);
           System.out.println("Vielen Dank für ihre Bewertung! ");
       }
    }

    static void getaverage() {

        if (ratings.isEmpty()) {
            System.out.println("Es liegen keine Bewertungen vor! ");
        } else {
            double sum = 0;
            for (int rating : ratings) {
                sum += rating;
            }
            double average = sum / ratings.size();
            System.out.println("Durchschnittliche Bewertung: " + average);
        }
    }

   static void leavecomment() {
        String comment = "";
       Scanner eingaben2 = new Scanner(System.in);
       System.out.println("Bitte geben Sie ihren Kommentar ein: ");
       comment = eingaben2.nextLine();
       System.out.println(comment);
   }




}
