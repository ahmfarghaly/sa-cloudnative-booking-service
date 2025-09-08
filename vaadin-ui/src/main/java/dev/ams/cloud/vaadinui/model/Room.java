package dev.ams.cloud.vaadinui.model;

import lombok.Data;

@Data
public class Room {
    private Long roomId;
    private String roomNumber;
    private String name;
    private String bedInfo;
}
