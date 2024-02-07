package fra.uas;

import fra.uas.model.User;
import fra.uas.model.UserDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import fra.uas.model.Review;
import fra.uas.model.RatingDTO;

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
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String url = "http://localhost:9090/users/create";
        HttpEntity<User> request = new HttpEntity<>(user);
        try {
            return restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Username already exists");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user) {
        String url = "http://localhost:9090/users/login";
        HttpEntity<UserDTO> request = new HttpEntity<>(user);
        try {
            return restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    @RequestMapping(value = "/protected", method = RequestMethod.GET)
    public ResponseEntity<?> protectedEndpoint(@RequestHeader("Authorization") String authToken) {
        String url = "http://localhost:9090/users/protected";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        String url = "http://localhost:9090/users/delete";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody UserDTO newPasswordDTO) {
        String url = "http://localhost:9090/users/change-password";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<UserDTO> request = new HttpEntity<>(newPasswordDTO, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    @RequestMapping(value = "/change-username", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUsername(@RequestHeader("Authorization") String token, @RequestBody UserDTO newUsernameDTO) {
        String url = "http://localhost:9090/users/change-username";
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
        String url = "http://localhost:9090/users/list";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }


    @PostMapping(value = "/create-booking")
    public ResponseEntity<?> createBookingThroughGateway(@RequestBody Map<String, Object> bookingData, @RequestHeader("Authorization") String authToken) {
        // Abrufen des Benutzernamens vom User Service
        String usernameUrl = "http://localhost:9090/users/get-username";
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
        String usernameUrl = "http://localhost:9090/users/get-username";
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
        String usernameUrl = "http://localhost:9090/users/get-username";
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


    @GetMapping("/booking/{bookingId}/invoice")
    public ResponseEntity<?> generateInvoiceThroughGateway(@RequestHeader("Authorization") String authToken, @PathVariable int bookingId) {
        // Extrahieren des Benutzernamens aus dem Authentifizierungs-Token
        String usernameUrl = "http://localhost:9090/users/get-username";
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
    
        // Weiterleiten der Anfrage an den Invoice-Service mit dem extrahierten Benutzernamen und der Buchungs-ID
        String invoiceUrl = "http://localhost:9092/invoice/generate/" + bookingId + "?username=" + username;
        try {
            ResponseEntity<?> invoiceResponse = restTemplate.getForEntity(invoiceUrl, String.class);
            return invoiceResponse;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Generieren der Rechnung: " + e.getStatusText());
        }
    }
    @PostMapping("/add-rating")
    public ResponseEntity<?> addRatingThroughGateway(@RequestBody Review review, @RequestHeader("Authorization") String authToken) {
        // Überprüfen, ob ein gültiges Authentifizierungstoken vorhanden ist
//        if (authToken == null || authToken.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Authentication token is required");
//        }

        // Abrufen des Benutzernamens vom User Service
        String usernameUrl = "http://localhost:9090/users/get-username";
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



        // Vorbereitung der Anfrage an das Rating-Service
        String ratingUrl = "http://localhost:9092/api/ratings/add";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Review> requestEntity = new HttpEntity<>(review, headers);

        try {
            // Senden der Bewertungsanfrage an das Rating-Service
            return restTemplate.postForEntity(ratingUrl, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Hinzufügen der Bewertung: " + e.getStatusText());
        }
    }

    @GetMapping("/average-rating")
    public ResponseEntity<?> getAverageRatingThroughGateway(@RequestHeader("Authorization") String authToken) {
        // Überprüfen, ob ein gültiges Authentifizierungstoken vorhanden ist
//        if (authToken == null || authToken.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Authentication token is required");
//        }

        // Abrufen des Benutzernamens vom User Service
        String usernameUrl = "http://localhost:9090/users/get-username";
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

        // Vorbereitung der Anfrage an das Rating-Service
        String averageRatingUrl = "http://localhost:9092/api/ratings/average";
        HttpHeaders averageRatingHeaders = new HttpHeaders();
        averageRatingHeaders.set("Authorization", authToken);
        HttpEntity<?> averageRatingRequest = new HttpEntity<>(averageRatingHeaders);

        try {
            // Senden der Anfrage für die durchschnittliche Bewertung an das Rating-Service
            return restTemplate.exchange(averageRatingUrl, HttpMethod.GET, averageRatingRequest, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen der durchschnittlichen Bewertung: " + e.getStatusText());
        }
    }
    @DeleteMapping("/ratings/{reviewId}")
    public ResponseEntity<?> deleteRating(@PathVariable Long reviewId, @RequestHeader("Authorization") String authToken) {
        // Führt Authentifizierung und Autorisierung durch, um sicherzustellen, dass der Benutzer berechtigt ist, die Bewertung zu löschen

        try {
            // Senden der DELETE-Anfrage an den Rating-Service, um die Bewertung zu löschen
            String ratingServiceUrl = "http://localhost:9092/api/ratings/" + reviewId;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authToken);
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<?> response = restTemplate.exchange(ratingServiceUrl, HttpMethod.DELETE, requestEntity, String.class);
            
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Löschen der Bewertung: " + e.getStatusText());
        }
    }

}
