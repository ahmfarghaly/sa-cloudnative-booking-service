package dev.ams.cloud.vaadinui.service;

import dev.ams.cloud.vaadinui.model.RoomReservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RoomReservationService {
    private final RestTemplate restTemplate;
    private final String reservationServiceUrl;

    public RoomReservationService(
            RestTemplate restTemplate,
            @Value("${room.reservation.service.url:http://localhost:8083/roomReservations}") String reservationServiceUrl) {
        this.restTemplate = restTemplate;
        this.reservationServiceUrl = reservationServiceUrl;
    }

    public List<RoomReservation> getRoomReservations(String date) {
        String url = date != null ? 
                String.format("%s?date=%s", reservationServiceUrl, date) :
                reservationServiceUrl;
                
        ResponseEntity<List<RoomReservation>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}
