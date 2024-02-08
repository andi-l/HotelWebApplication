package fra.uas;

import fra.uas.Model.Rating;
import fra.uas.model.User;
import fra.uas.model.UserDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import fra.uas.Model.Review;
import fra.uas.Model.RatingDTO;
import fra.uas.Model.Booking;

import java.util.Collection;
import java.util.Map;


@RestController
@RequestMapping("/gateway")
public class GatewayController {


    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // This endpoint creates a new user.
    // It takes a User object and sends a POST request to the user service.
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String url = "http://localhost:9090/user";
        HttpEntity<User> request = new HttpEntity<>(user);
        try {
            return restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Username already exists");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user) {
        String url = "http://localhost:9090/login";
        HttpEntity<UserDTO> request = new HttpEntity<>(user);
        try {
            return restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    @RequestMapping(value = "/protected", method = RequestMethod.GET)
    public ResponseEntity<?> protectedEndpoint(@RequestHeader("Authorization") String authToken) {
        String url = "http://localhost:9090/protected";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        String url = "http://localhost:9090/user";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    @RequestMapping(value = "/password", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody UserDTO newPasswordDTO) {
        String url = "http://localhost:9090/password";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<UserDTO> request = new HttpEntity<>(newPasswordDTO, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    @RequestMapping(value = "/username", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUsername(@RequestHeader("Authorization") String token, @RequestBody UserDTO newUsernameDTO) {
        String url = "http://localhost:9090/username";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<UserDTO> request = new HttpEntity<>(newUsernameDTO, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> getUserList(@RequestHeader("Authorization") String token) {
        String url = "http://localhost:9090/list";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }


    @PostMapping(value = "/booking")
    public ResponseEntity<?> createBookingThroughGateway(@RequestBody Map<String, Object> bookingData, @RequestHeader("Authorization") String authToken) {
        // Abrufen des Benutzernamens vom User Service
        String usernameUrl = "http://localhost:9090/username";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> usernameRequest = new HttpEntity<>(headers);
        ResponseEntity<String> usernameResponse;
        try {
            usernameResponse = restTemplate.exchange(usernameUrl, HttpMethod.GET, usernameRequest, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen des Benutzernamens: " + e.getStatusText());
        }

        if (usernameResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(usernameResponse.getStatusCode()).body("Benutzer nicht gefunden oder ungültiges Token");
        }

        String username = usernameResponse.getBody();

        // Add the username to the booking data
        bookingData.put("username", username);

        // Vorbereitung der Anfrage an den Booking Service
        String bookingUrl = "http://localhost:9091/booking";
        HttpHeaders bookingHeaders = new HttpHeaders();
        bookingHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> bookingRequestEntity = new HttpEntity<>(bookingData, bookingHeaders);

        try {
            // Senden der Buchungsanfrage an den Booking Service
            return restTemplate.postForEntity(bookingUrl, bookingRequestEntity, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler bei der Erstellung der Buchung. Passen Sie bitte die Daten an." + e.getStatusText());
        }
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getBookingByUsernameAndIdThroughGateway(@RequestHeader("Authorization") String authToken, @PathVariable int bookingId) {
        // Extrahieren des Benutzernamens aus dem Authentifizierungs-Token
        String usernameUrl = "http://localhost:9090/username";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> usernameRequest = new HttpEntity<>(headers);
        ResponseEntity<String> usernameResponse;
        try {
            usernameResponse = restTemplate.exchange(usernameUrl, HttpMethod.GET, usernameRequest, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen des Benutzernamens: " + e.getStatusText());
        }

        if (usernameResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(usernameResponse.getStatusCode()).body("Benutzer nicht gefunden oder ungültiges Token");
        }

        String username = usernameResponse.getBody();

        // Weiterleiten der Anfrage an den Booking-Service mit dem extrahierten Benutzernamen und der Buchungs-ID
        String bookingUrl = "http://localhost:9091/booking/" + username + "/" + bookingId;
        try {
            // Hier könnte zusätzlich eine HttpEntity mit weiteren notwendigen Headers konfiguriert werden, falls nötig
            ResponseEntity<?> bookingResponse = restTemplate.getForEntity(bookingUrl, String.class);
            return bookingResponse;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen der Buchung: " + e.getStatusText());
        }
    }

    @GetMapping(value = "/booking")
    public ResponseEntity<?> getAllBookingsThroughGateway(@RequestHeader("Authorization") String authToken) {
        // Abrufen des Benutzernamens vom User Service
        String usernameUrl = "http://localhost:9090/username";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> usernameRequest = new HttpEntity<>(headers);
        ResponseEntity<String> usernameResponse;
        try {
            usernameResponse = restTemplate.exchange(usernameUrl, HttpMethod.GET, usernameRequest, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen des Benutzernamens: " + e.getStatusText());
        }

        if (usernameResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(usernameResponse.getStatusCode()).body("Benutzer nicht gefunden oder ungültiges Token");
        }

        String username = usernameResponse.getBody();

        // Anfrage an den Booking Service senden, um alle Buchungen für den Benutzer abzurufen
        String bookingUrl = "http://localhost:9091/booking/" + username;
        try {
            ResponseEntity<String> bookingResponse = restTemplate.exchange(bookingUrl, HttpMethod.GET, null, String.class);
            return bookingResponse;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler bei der Abfrage der Buchungen: " + e.getStatusText());
        }
    }

    @GetMapping(value = "/availability")
    public ResponseEntity<?> checkAvailabilityThroughGateway(
            @RequestParam("checkInDate") String checkInDate,
            @RequestParam("checkOutDate") String checkOutDate,
            @RequestParam("capacity") int capacity) {

        // Konstruktion der URL mit Parametern für den Booking-Service
        String availabilityUrl = UriComponentsBuilder.fromHttpUrl("http://localhost:9091/availability")
                .queryParam("checkInDate", checkInDate)
                .queryParam("checkOutDate", checkOutDate)
                .queryParam("capacity", capacity)
                .toUriString();

        try {
            // Senden der GET-Anfrage an den Booking-Service und Rückgabe der Antwort
            ResponseEntity<String> response = restTemplate.getForEntity(availabilityUrl, String.class);
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler bei der Verfügbarkeitsprüfung: " + e.getStatusText());
        }
    }


    @GetMapping("/booking/invoice/{bookingId}")
    public ResponseEntity<?> generateInvoiceThroughGateway(@RequestHeader("Authorization") String authToken, @PathVariable int bookingId) {
        // Extrahieren des Benutzernamens aus dem Authentifizierungs-Token
        String usernameUrl = "http://localhost:9090/username";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> usernameRequest = new HttpEntity<>(headers);
        ResponseEntity<String> usernameResponse;
        try {
            usernameResponse = restTemplate.exchange(usernameUrl, HttpMethod.GET, usernameRequest, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen des Benutzernamens: " + e.getStatusText());
        }

        if (usernameResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(usernameResponse.getStatusCode()).body("Benutzer nicht gefunden oder ungültiges Token");
        }

        if (usernameResponse.getStatusCode().is2xxSuccessful()) {
            String username = usernameResponse.getBody();
            String invoiceUrl = "http://localhost:9091/booking/" + bookingId + "/invoice?username=" + username;
            try {
                return restTemplate.getForEntity(invoiceUrl, String.class);
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Generieren der Rechnung: " + e.getStatusText());
            }
        } else {
            return ResponseEntity.status(usernameResponse.getStatusCode()).body("Benutzer nicht gefunden oder ungültiges Token");
        }
    }
    @PostMapping("/rating")
    public ResponseEntity<?> addRatingThroughGateway(@RequestBody Map<String, Object> ratingData, @RequestHeader("Authorization") String authToken) {
        // Extrahieren des Benutzernamens aus dem Authentifizierungs-Token
        String usernameUrl = "http://localhost:9090/username";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> usernameRequest = new HttpEntity<>(headers);
        ResponseEntity<String> usernameResponse;
        ResponseEntity<Booking> bookingResponse;
        try {
            usernameResponse = restTemplate.exchange(usernameUrl, HttpMethod.GET, usernameRequest, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen des Benutzernamens: " + e.getStatusText());
        }

        if (usernameResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(usernameResponse.getStatusCode()).body("Benutzer nicht gefunden oder ungültiges Token");
        }

        String username = usernameResponse.getBody();

        // Extrahieren der bookingId und des Review aus der HashMap
        Integer bookingId = (Integer) ratingData.get("bookingId");
        Map<String, Object> reviewMap = (Map<String, Object>) ratingData.get("review");

        // Überprüfen, ob Sterne vorhanden und gültig sind
        if (reviewMap.get("stars") == null) {
            return ResponseEntity.badRequest().body("Sternebewertung ist erforderlich.");
        }
        Integer stars = (Integer) reviewMap.get("stars");
        String comment = (String) reviewMap.get("comment");
        Review review = new Review(stars, comment);

        // Weiterleiten der Anfrage an den Booking-Service mit dem extrahierten Benutzernamen und der Buchungs-ID
        String bookingUrl = "http://localhost:9091/booking/" + username + "/" + bookingId;
        try {
            bookingResponse = restTemplate.getForEntity(bookingUrl, Booking.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen der Buchung: " + e.getStatusText());
        }

        Booking bookingForReview = bookingResponse.getBody();

        RatingDTO ratingDTO = new RatingDTO(bookingForReview, review);

        // Preparation of the request to the rating service
        String ratingUrl = "http://localhost:9092/rating";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(ratingDTO, headers);

        try {
            // Sending the rating request to the rating service
            return restTemplate.postForEntity(ratingUrl, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Hinzufügen der Bewertung: " + e.getStatusText());
        }
    }
    //Display Rating
    @GetMapping("/rating")
    public ResponseEntity<?> getAllRatingsThroughGateway() {
        String ratingServiceUrl = "http://localhost:9092/rating";

        ResponseEntity<Collection> responseEntity = restTemplate.getForEntity(ratingServiceUrl, Collection.class);
        if (responseEntity.getBody() == null || responseEntity.getBody().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseEntity.getBody());
    }

    //Show the average Rating 
    @GetMapping("/average")
    public ResponseEntity<?> getAverageRatingThroughGateway() {
        String averageRatingUrl = "http://localhost:9092/average";
        HttpHeaders averageRatingHeaders = new HttpHeaders();
        HttpEntity<?> averageRatingRequest = new HttpEntity<>(averageRatingHeaders);

        try {
            return restTemplate.exchange(averageRatingUrl, HttpMethod.GET, averageRatingRequest, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen der durchschnittlichen Bewertung: " + e.getStatusText());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Allgemeiner Fehler beim Abrufen der durchschnittlichen Bewertung: " + e.getMessage());
        }
    }
    @DeleteMapping("/ratings/{reviewId}")
    public ResponseEntity<?> deleteRating(@PathVariable Long reviewId, @RequestHeader("Authorization") String authToken) {
        
        // Performs authentication and authorization to ensure the user is authorized to delete the review
        try {
            // Sending the DELETE request to the rating service to delete the rating
            String ratingServiceUrl = "http://localhost:9092/api/ratings/" + reviewId;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authToken);
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<?> response = restTemplate.exchange(ratingServiceUrl, HttpMethod.DELETE, requestEntity, String.class);
            
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Löschen der Bewertung:" + e.getStatusText());
        }
    }

}
