package dev.ams.cloud.roomreservationservice.client.room;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@FeignClient(name = "room-service")
public interface RoomServiceClient {

    String ROOMS_URL_PART = "/rooms";
    String SLASH = "/";

    @GetMapping(ROOMS_URL_PART)
    List<Room> getAll();

    @PostMapping(ROOMS_URL_PART)
    Room addRoom(@RequestBody Room room);

    @GetMapping(ROOMS_URL_PART + SLASH + "{id}")
    Room getRoom(@PathVariable("id") long id);

    @PutMapping(ROOMS_URL_PART + SLASH + "{id}")
    void updateRoom(@PathVariable("id") long id, @RequestBody Room room);

    @DeleteMapping(ROOMS_URL_PART + SLASH + "{id}")
    void deleteRoom(@PathVariable("id") long id);
}
