package fra.uas;

import fra.uas.model.User;
import fra.uas.model.UserDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


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

    // Neuer Endpunkt im GatewayController für das Erstellen einer Buchung mit Benutzernamen aus dem User Service
@RequestMapping(value = "/create-booking-with-token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> createBookingWithToken(@RequestBody BookingDTO bookingRequest, @RequestHeader("Authorization") String authToken) {
    // Abrufen des Benutzernamens vom User Service
    String usernameUrl = "http://localhost:9090/get-username";
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

    // Erstellen einer Buchung mit dem erhaltenen Benutzernamen
    String bookingUrl = "http://localhost:9091/booking";
    BookingDTO bookingDTO = new BookingDTO(bookingRequest, username); // Annahme, dass BookingDTO eine Methode zur Einbeziehung des Benutzernamens hat
    HttpEntity<BookingDTO> bookingRequestEntity = new HttpEntity<>(bookingDTO);

    try {
        return restTemplate.postForEntity(bookingUrl, bookingRequestEntity, String.class);
    } catch (HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body("Fehler bei der Erstellung der Buchung: " + e.getStatusText());
    }
}


}
