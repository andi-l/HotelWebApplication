package fra.uas;

import fra.uas.model.*;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/gateway")
public class GatewayController {


    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Endpoint to create a new user by sending a POST request to the User Service
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String url = "http://localhost:9090/user";
        HttpEntity<User> request = new HttpEntity<>(user);
        try {
            // Sends the user data to create a new user
            return restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Username already exists");
        }
    }

    // Endpoint for user login. Sends POST request to User Service for authentication
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user) {
        String url = "http://localhost:9090/login";
        HttpEntity<UserDTO> request = new HttpEntity<>(user);
        try {
            // Attempts to authenticate the user with the provided credentials
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

    // Endpoint to delete a user. Sends DELETE request to User Service
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        String url = "http://localhost:9090/user";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            // Deletes the user associated with the token
            return restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    // Endpoint to change the user's password. Sends PUT request to User Service
    @RequestMapping(value = "/password", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody UserDTO newPasswordDTO) {
        String url = "http://localhost:9090/password";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<UserDTO> request = new HttpEntity<>(newPasswordDTO, headers);
        try {
            // Changes the user's password
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    // Endpoint to change the user's username. Sends PUT request to User Service
    @RequestMapping(value = "/username", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUsername(@RequestHeader("Authorization") String token, @RequestBody UserDTO newUsernameDTO) {
        String url = "http://localhost:9090/username";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<UserDTO> request = new HttpEntity<>(newUsernameDTO, headers);
        try {
            // Changes the user's username
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    // Endpoint to get the list of users. Sends GET request to User Service
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> getUserList(@RequestHeader("Authorization") String token) {
        String url = "http://localhost:9090/list";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            // Retrieves the list of users
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getStatusText());
        }
    }

    // Endpoint to create a booking through the gateway. It collects booking data and user authentication token.
    // It then retrieves the username from the User Service and sends a POST request to the Booking Service.
    @PostMapping(value = "/booking")
    public ResponseEntity<?> createBookingThroughGateway(@RequestBody Map<String, Object> bookingData, @RequestHeader("Authorization") String authToken) {
        var reso = protectedEndpoint(authToken);
        if (reso.getStatusCode().is2xxSuccessful()) {

            // Abrufen des Benutzernamens vom User Service
            String usernameUrl = "http://localhost:9090/username";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authToken);
            HttpEntity<?> usernameRequest = new HttpEntity<>(headers);
            ResponseEntity<String> usernameResponse;
            try {
                // Obtain the username from the User Service
                usernameResponse = restTemplate.exchange(usernameUrl, HttpMethod.GET, usernameRequest, String.class);
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen des Benutzernamens: " + e.getStatusText());
            }

            if (usernameResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(usernameResponse.getStatusCode()).body("Benutzer nicht gefunden oder ungültiges Token");
            }

            String username = usernameResponse.getBody();
            bookingData.put("username", username);// Add the retrieved username to the booking data

            // Prepare the request to the Booking Service
            String bookingUrl = "http://localhost:9091/booking";
            HttpHeaders bookingHeaders = new HttpHeaders();
            bookingHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> bookingRequestEntity = new HttpEntity<>(bookingData, bookingHeaders);

            try {
                // Send the booking request to the Booking Service
                return restTemplate.postForEntity(bookingUrl, bookingRequestEntity, String.class);
            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode()).body("Fehler bei der Erstellung der Buchung. Passen Sie bitte die Daten an." + e.getStatusText());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to login to book a room");
        }
    }

    // Endpoint to retrieve a booking by ID through the gateway. It uses the user's authentication token to
    // fetch the username from the User Service and then sends a GET request to the Booking Service.
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getBookingByUsernameAndIdThroughGateway(@RequestHeader("Authorization") String authToken, @PathVariable int bookingId) {
        String usernameUrl = "http://localhost:9090/username";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> usernameRequest = new HttpEntity<>(headers);
        ResponseEntity<String> usernameResponse;
        try {
            // Retrieve the username from the User Service
            usernameResponse = restTemplate.exchange(usernameUrl, HttpMethod.GET, usernameRequest, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen des Benutzernamens: " + e.getStatusText());
        }

        if (usernameResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(usernameResponse.getStatusCode()).body("Benutzer nicht gefunden oder ungültiges Token");
        }

        String username = usernameResponse.getBody();

        // Forward the request to the Booking Service with the extracted username and booking ID
        String bookingUrl = "http://localhost:9091/booking/" + username + "/" + bookingId;
        try {
            // Hier könnte zusätzlich eine HttpEntity mit weiteren notwendigen Headers konfiguriert werden, falls nötig
            ResponseEntity<?> bookingResponse = restTemplate.getForEntity(bookingUrl, String.class);
            return bookingResponse;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen der Buchung: " + e.getStatusText());
        }
    }

    // Endpoint to retrieve all bookings for a user through the gateway. It first retrieves the username
    // from the User Service using the user's authentication token, then sends a GET request to the Booking Service.
    @GetMapping(value = "/bookings")
    public ResponseEntity<?> getAllBookingsThroughGateway(@RequestHeader("Authorization") String authToken) {
        // Abrufen des Benutzernamens vom User Service
        String usernameUrl = "http://localhost:9090/username";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> usernameRequest = new HttpEntity<>(headers);
        ResponseEntity<String> usernameResponse;
        try {
            // Retrieve the username from the User Service
            usernameResponse = restTemplate.exchange(usernameUrl, HttpMethod.GET, usernameRequest, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler beim Abrufen des Benutzernamens: " + e.getStatusText());
        }
        if (usernameResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(usernameResponse.getStatusCode()).body("Benutzer nicht gefunden oder ungültiges Token");
        }
        String username = usernameResponse.getBody();
        String bookingUrl = "http://localhost:9091/booking/" + username;
        try {
            // Send the request to the Booking Service to retrieve all bookings for the user
            ResponseEntity<List> bookingResponse = restTemplate.exchange(bookingUrl, HttpMethod.GET, null, List.class);
            return bookingResponse;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler bei der Abfrage der Buchungen: " + e.getStatusText());
        }
    }

    // Endpoint to check room availability through the gateway. It constructs a URL with parameters for the Booking Service
    // and sends a GET request to check availability based on the provided criteria.
    @GetMapping(value = "/availability")
    public ResponseEntity<?> checkAvailabilityThroughGateway(
            @RequestParam("checkInDate") String checkInDate,
            @RequestParam("checkOutDate") String checkOutDate,
            @RequestParam("capacity") int capacity) {

        // Construct the URL with parameters for the Booking Service
        String availabilityUrl = UriComponentsBuilder.fromHttpUrl("http://localhost:9091/availability")
                .queryParam("checkInDate", checkInDate)
                .queryParam("checkOutDate", checkOutDate)
                .queryParam("capacity", capacity)
                .toUriString();

        try {
            // Send the GET request to the Booking Service and return the response
            ResponseEntity<String> response = restTemplate.getForEntity(availabilityUrl, String.class);
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler bei der Verfügbarkeitsprüfung. " + e.getStatusText());
        }
    }

    @GetMapping("/invoice/{bookingId}")
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
            var userBookings = getAllBookingsThroughGateway(authToken).getBody();
            System.out.println("Hallo: " + userBookings);
            assert userBookings != null;
            if (userBookings.getClass().isAssignableFrom(List.class)) {
                System.out.println("Guten Tag");
            }
            System.out.println(userBookings.getClass());

            if (userBookings == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have no bookings to generate an invoice for.");
            }

            var rooms = getAllRooms().getBody();
            System.out.println("Hallo2: " + rooms);
            assert rooms != null;
            if (rooms.getClass().isAssignableFrom(List.class)) {
                System.out.println("Guten Tag2");
            }
            System.out.println("rooms" + rooms.getClass());

            if (rooms == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are no rooms in this hotel");
            }

            Hotel hoteldto = new Hotel();
            hoteldto.setBookings((ArrayList<Booking>) userBookings);
            hoteldto.setRooms((ArrayList<Room>) rooms);

            HttpEntity<Object> requestEntity = new HttpEntity<>(hoteldto);
            String username = usernameResponse.getBody();
            String invoiceUrl = "http://localhost:9093/invoiceservice/" + bookingId + "/" + username;

            try {
                //System.out.println("here");
                return restTemplate.postForEntity(invoiceUrl, requestEntity, String.class);

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
    @GetMapping("/ratings")
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

    public ResponseEntity<?> getAllRooms() {
        String bookingServiceUrl = "http://localhost:9091/hotel/rooms";

        ResponseEntity<List> responseEntity = restTemplate.getForEntity(bookingServiceUrl, List.class);
        if (responseEntity.getBody() == null || responseEntity.getBody().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseEntity.getBody());
    }

}
