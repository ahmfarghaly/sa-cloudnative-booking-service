package dev.ams.cloud.vaadinui.service;

import dev.ams.cloud.vaadinui.model.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReservationService {
    private final RestTemplate restTemplate;
    private final String reservationServiceUrl;

    public ReservationService(
            RestTemplate restTemplate,
            @Value("${reservation.service.url:http://localhost:8081/reservations}") String reservationServiceUrl) {
        this.restTemplate = restTemplate;
        this.reservationServiceUrl = reservationServiceUrl;
    }

    public Reservation createReservation(Reservation reservation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Reservation> request = new HttpEntity<>(reservation, headers);
        
        return restTemplate.postForObject(reservationServiceUrl, request, Reservation.class);
    }

    public List<Reservation> getReservations(String date, Long guestId) {
        StringBuilder urlBuilder = new StringBuilder(reservationServiceUrl);
        boolean firstParam = true;
        
        if (date != null) {
            urlBuilder.append(firstParam ? "?" : "&").append("date=").append(date);
            firstParam = false;
        }
        
        if (guestId != null) {
            urlBuilder.append(firstParam ? "?" : "&").append("guestId=").append(guestId);
        }
        
        ResponseEntity<List<Reservation>> response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}
