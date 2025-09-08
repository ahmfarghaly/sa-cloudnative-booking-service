package dev.ams.cloud.roomreservationservice.client.guest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@FeignClient(name = "guest-service", fallback = GuestServiceFallback.class)
public interface GuestServiceClient {

    String GUESTS_URL_PART = "/guests";
    String SLASH = "/";

    @GetMapping(GUESTS_URL_PART)
    List<Guest> getAll();

    @PostMapping(GUESTS_URL_PART)
    Guest addGuest(@RequestBody Guest guest);

    @GetMapping(GUESTS_URL_PART + SLASH + "{id}")
    Guest getGuest(@PathVariable("id") long id);

    @PutMapping(GUESTS_URL_PART + SLASH + "{id}")
    void updateGuest( @PathVariable("id") long id, @RequestBody Guest guest);

    @DeleteMapping(GUESTS_URL_PART + SLASH + "{id}")
    void deleteGuest(@PathVariable("id") long id);

}
