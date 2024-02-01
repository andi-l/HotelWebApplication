package fra.uas;

import fra.uas.model.User;
import fra.uas.model.UserDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
                return ResponseEntity.status(e.getStatusCode()).body("Fehler bei der Erstellung der Buchung: " + e.getStatusText());
            }
        }
    
        @GetMapping("/booking/{bookingId}")
        public ResponseEntity<?> getBookingByIdThroughGateway(@PathVariable int bookingId, @RequestHeader("Authorization") String authToken) {
            // URL des Buchungsservices, wo die Buchungsinformationen abgerufen werden
            String bookingServiceUrl = "http://localhost:9091/booking/" + bookingId;
        
            // Vorbereitung der Header für die Weiterleitung der Authentifizierung
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
        
            try {
                // Weiterleitung der Anfrage an den Buchungsservice
                ResponseEntity<String> response = restTemplate.exchange(bookingServiceUrl, HttpMethod.GET, entity, String.class);
                return ResponseEntity.ok().body(response.getBody());
            } catch (HttpClientErrorException e) {
                // Behandlung von Fehlern, z.B. wenn die Buchung nicht gefunden wird oder bei Authentifizierungsproblemen
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
        String bookingUrl = "http://localhost:9091/booking?username=" + username;
        try {
            ResponseEntity<String> bookingResponse = restTemplate.exchange(bookingUrl, HttpMethod.GET, null, String.class);
            return bookingResponse;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Fehler bei der Abfrage der Buchungen: " + e.getStatusText());
        }
    }



}
