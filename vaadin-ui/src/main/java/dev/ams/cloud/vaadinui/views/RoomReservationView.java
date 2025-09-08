package dev.ams.cloud.vaadinui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.ams.cloud.vaadinui.model.RoomReservation;
import dev.ams.cloud.vaadinui.service.RoomReservationService;
import dev.ams.cloud.vaadinui.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Route(value = "room-reservations", layout = MainLayout.class)
public class RoomReservationView extends VerticalLayout {
    private final RoomReservationService reservationService;
    private final Grid<RoomReservation> grid = new Grid<>(RoomReservation.class);
    private final DatePicker datePicker = new DatePicker("Date");
    
    @Autowired
    public RoomReservationView(RoomReservationService reservationService) {
        this.reservationService = reservationService;
        
        configureGrid();
        configureDatePicker();
        
        Button searchButton = new Button("Search", e -> loadData());
        
        HorizontalLayout toolbar = new HorizontalLayout(datePicker, searchButton);
        toolbar.setAlignItems(Alignment.BASELINE);
        
        add(new H1("Room Reservations"), toolbar, grid);
        loadData();
    }
    
    private void configureGrid() {
        grid.removeAllColumns();
        grid.addColumn(RoomReservation::getRoomNumber).setHeader("Room Number");
        grid.addColumn(RoomReservation::getName).setHeader("Room Name");
        grid.addColumn(RoomReservation::getBedInfo).setHeader("Bed Info");
        grid.addColumn(r -> r.getFirstName() + " " + r.getLastName()).setHeader("Guest");
        grid.addColumn(RoomReservation::getDate).setHeader("Date");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
    
    private void configureDatePicker() {
        datePicker.setValue(LocalDate.now());
    }
    
    private void loadData() {
        String date = datePicker.getValue().format(DateTimeFormatter.ISO_DATE);
        grid.setItems(reservationService.getRoomReservations(date));
    }
}
