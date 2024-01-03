package tech.titans.hotel.Service;

import java.util.Scanner;

public class RatingService {
    public static void main(String[] args) {

        // Ratingservice
        bewerteHotel();
    }

    static void bewerteHotel() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bitte geben Sie Ihre Sternebewertung (1-5) für das Hotel ein:");
        int sterneBewertung = scanner.nextInt();

        // Überprüfen ob die Bewertung im gültigen Bereich liegt
        if (sterneBewertung < 1 || sterneBewertung > 5) {
            System.out.println("Ungültige Bewertung. Bitte geben Sie eine Bewertung zwischen 1 und 5 ein.");
            return;
        }

        // Optional: Kommentar abfragen
        scanner.nextLine(); // Dummy-Lesen
        System.out.println("Möchten Sie einen Kommentar abgeben? (Ja/Nein):");
        String antwort = scanner.nextLine();

        String kommentar = "";
        if (antwort.equalsIgnoreCase("Ja")) {
            System.out.println("Geben Sie Ihren Kommentar ein:");
            kommentar = scanner.nextLine();
        }

        System.out.println("Vielen Dank für Ihre Bewertung!");
        System.out.println("Bewertung: " + sterneBewertung + " Sterne");
        if (!kommentar.isEmpty()) {
            System.out.println("Kommentar: " + kommentar);
}

scanner.close();

}
}


