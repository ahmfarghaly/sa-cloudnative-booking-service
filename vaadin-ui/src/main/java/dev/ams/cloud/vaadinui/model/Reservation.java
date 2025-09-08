package dev.ams.cloud.vaadinui.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Reservation {
    private Long reservationId;
    private Long roomId;
    private Long guestId;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
