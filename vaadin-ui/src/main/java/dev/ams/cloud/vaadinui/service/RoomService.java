package dev.ams.cloud.vaadinui.service;

import dev.ams.cloud.vaadinui.model.Room;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RoomService {
    private final RestTemplate restTemplate;
    private final String roomServiceUrl;

    public RoomService(
            RestTemplate restTemplate,
            @Value("${room.service.url:http://localhost:8082/rooms}") String roomServiceUrl) {
        this.restTemplate = restTemplate;
        this.roomServiceUrl = roomServiceUrl;
    }

    public List<Room> getAllRooms() {
        ResponseEntity<List<Room>> response = restTemplate.exchange(
                roomServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}
