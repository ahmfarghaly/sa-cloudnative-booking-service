package dev.ams.cloud.vaadinui.service;

import dev.ams.cloud.vaadinui.model.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GuestService {
    private final RestTemplate restTemplate;
    private final String guestServiceUrl;

    public GuestService(
            RestTemplate restTemplate,
            @Value("${guest.service.url:http://localhost:8080/guests}") String guestServiceUrl) {
        this.restTemplate = restTemplate;
        this.guestServiceUrl = guestServiceUrl;
    }

    public List<Guest> getAllGuests() {
        ResponseEntity<List<Guest>> response = restTemplate.exchange(
                guestServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public Guest getGuestById(Long id) {
        String url = guestServiceUrl + "/" + id;
        return restTemplate.getForObject(url, Guest.class);
    }
}
