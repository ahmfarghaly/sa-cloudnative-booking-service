package dev.ams.cloud.vaadinui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.ams.cloud.vaadinui.model.Guest;
import dev.ams.cloud.vaadinui.model.Reservation;
import dev.ams.cloud.vaadinui.model.Room;
import dev.ams.cloud.vaadinui.service.GuestService;
import dev.ams.cloud.vaadinui.service.ReservationService;
import dev.ams.cloud.vaadinui.service.RoomService;
import dev.ams.cloud.vaadinui.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Route(value = "create-reservation", layout = MainLayout.class)
public class CreateReservationView extends VerticalLayout {
    private final GuestService guestService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    
    private final ComboBox<Guest> guestComboBox = new ComboBox<>("Guest");
    private final ComboBox<Room> roomComboBox = new ComboBox<>("Room");
    private final DatePicker datePicker = new DatePicker("Date");
    
    @Autowired
    public CreateReservationView(GuestService guestService, 
                                RoomService roomService,
                                ReservationService reservationService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        
        FormLayout form = new FormLayout();
        form.add(guestComboBox, roomComboBox, datePicker);
        
        Button submitButton = new Button("Create Reservation", event -> createReservation());
        
        add(form, submitButton);
        
        configureForm();
        loadData();
    }
    
    private void configureForm() {
        guestComboBox.setItemLabelGenerator(guest -> guest.getFirstName() + " " + guest.getLastName());
        roomComboBox.setItemLabelGenerator(room -> room.getRoomNumber() + " - " + room.getName());
        datePicker.setValue(LocalDate.now());
    }
    
    private void loadData() {
        guestComboBox.setItems(guestService.getAllGuests());
        roomComboBox.setItems(roomService.getAllRooms());
    }
    
    private void createReservation() {
        Guest guest = guestComboBox.getValue();
        Room room = roomComboBox.getValue();
        LocalDate date = datePicker.getValue();
        
        if (guest == null || room == null || date == null) {
            Notification.show("Please fill in all fields");
            return;
        }
        
        Reservation reservation = new Reservation();
        reservation.setGuestId(guest.getGuestId());
        reservation.setRoomId(room.getRoomId());
        reservation.setDate(java.sql.Date.valueOf(date));
        
        try {
            reservationService.createReservation(reservation);
            Notification.show("Reservation created successfully");
            clearForm();
        } catch (Exception e) {
            Notification.show("Error creating reservation: " + e.getMessage());
        }
    }
    
    private void clearForm() {
        guestComboBox.clear();
        roomComboBox.clear();
        datePicker.clear();
    }
}
