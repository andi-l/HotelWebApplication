### Projektbeschreibung

Das Projekt ist eine Webanwendung zur Verwaltung von Hotelbuchungen. Es ermöglicht Benutzern, Accounts zu erstellen, Verfügbarkeiten zu prüfen, Buchungen vorzunehmen, Bewertungen abzugeben und Rechnungen zu erhalten.

### Architektur

Unsere Webanwendung basiert auf einer Microservice-Architektur, bei der die Kernfunktionalitäten User, Booking, Rating und Invoice als unabhängige Services implementiert sind. Jeder dieser Services läuft auf einem eigenen Port, wodurch sie isoliert voneinander agieren können, was eine höhere Skalierbarkeit, Flexibilität und Wartbarkeit ermöglicht.

### Microservice-Struktur

- **User Service**: Verwaltung von Benutzerkonten und Authentifizierung.
- **Booking Service**: Verwaltung von Buchungen.
- **Rating Service**: Verwaltung von Bewertungen.
- **Invoice Service**: Generierung von Rechnungen.

Benutzer interagieren ausschließlich mit dem Gateway, der als zentraler Zugangspunkt dient. Der Gateway verknüpft die Anfragen des Benutzers mit den entsprechenden Microservices und leitet Antworten zurück zum Benutzer.

### Endpunkte

| HTTP-Methode | URI                                | HTTP Statuscodes                 |
|--------------|------------------------------------|----------------------------------|
| POST         | /gateway/user                      | 200 OK, 400 Bad Request          |
| POST         | /gateway/login                     | 200 OK, 400 Bad Request, 401 Unauthorized |
| DELETE       | /gateway/user                      | 200 OK, 401 Unauthorized, 404 Not Found |
| PUT          | /gateway/password                  | 200 OK, 400 Bad Request, 401 Unauthorized |
| PUT          | /gateway/username                  | 200 OK, 400 Bad Request, 401 Unauthorized |
| GET          | /gateway/list                      | 200 OK, 401 Unauthorized         |
| POST         | /gateway/booking                   | 200 OK, 400 Bad Request, 401 Unauthorized |
| GET          | /gateway/booking/{bookingId}       | 200 OK, 400 Bad Request, 401 Unauthorized, 404 Not Found |
| GET          | /gateway/booking                   | 200 OK, 401 Unauthorized         |
| GET          | /gateway/availability              | 200 OK, 400 Bad Request          |
| GET          | /gateway/booking/invoice/{bookingId} | 200 OK, 400 Bad Request, 401 Unauthorized, 404 Not Found |
| POST         | /gateway/rating                    | 200 OK, 400 Bad Request, 401 Unauthorized |
| GET          | /gateway/rating                    | 200 OK, 204 No Content           |
| GET          | /gateway/average                   | 200 OK, 204 No Content, 500 Internal Server Error |
| DELETE       | /gateway/ratings/{reviewId}        | 200 OK, 400 Bad Request, 401 Unauthorized, 404 Not Found |


### Codebeispiele und Erklärungen

- Benutzererstellung und Authentifizierung: Der Gateway Controller bietet Endpunkte für das Erstellen von Benutzerkonten (/user), das Einloggen (/login), wodurch er mithilfe eines Tokens durch die Anwendung navigieren kann, ohne sich erneut einloggen zu müssen.

- Buchungsmanagement: Benutzer können Buchungen erstellen (/booking), abrufen (/booking/{id}) und alle ihre Buchungen anzeigen (/booking). Es gibt auch einen Endpunkt zur Überprüfung der Verfügbarkeit von Zimmern basierend auf bestimmten Daten und Kapazitäten (/availability).

- Bewertungen und Rechnungen: Benutzer können Bewertungen zu ihren Buchungen abgeben (/rating) und eine Liste aller Bewertungen abrufen (/rating). Für jede Buchung kann auch eine Rechnung generiert werden (/booking/invoice/{id}).
