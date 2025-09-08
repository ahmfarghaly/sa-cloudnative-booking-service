package dev.ams.cloud.vaadinui.model;

import lombok.Data;

@Data
public class RoomReservation {
    private Long roomId;
    private Long guestId;
    private String roomNumber;
    private String name;
    private String bedInfo;
    private String date;
    private String firstName;
    private String lastName;
    private Long reservationId;
}
