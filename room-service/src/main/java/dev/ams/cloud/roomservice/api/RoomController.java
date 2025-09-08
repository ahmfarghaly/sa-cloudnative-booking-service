package dev.ams.cloud.roomservice.api;

import dev.ams.cloud.roomservice.data.Room;
import dev.ams.cloud.roomservice.data.RoomRepository;
import dev.ams.cloud.roomservice.error.BadReqeustException;
import dev.ams.cloud.roomservice.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@Slf4j
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public Iterable<Room> getAll() {
        log.info("getting all rooms");
        return this.roomRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room addRoom(@RequestBody Room room) {
        log.info("adding room {}", room);
        return this.roomRepository.save(room);
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable("id") long id) {
        log.info("getting details for room {}", id);
        Optional<Room> room = this.roomRepository.findById(id);
        if (room.isEmpty()) {
            throw new NotFoundException("id not found " + id);
        }
        return room.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoom(@PathVariable("id") long id, @RequestBody Room room) {
        log.info("updating room {}", room);
        if (id != room.getRoomId()) {
            throw new BadReqeustException("id in body doesn't match path");
        }
        this.roomRepository.save(room);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteRoom(@PathVariable("id") long id) {
        log.info("deleting room {}", id);
        this.roomRepository.deleteById(id);
    }
}
