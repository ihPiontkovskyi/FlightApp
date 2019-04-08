package gui.controllers.modelControllers;

import gui.controllers.customObjects.ComboBoxCell;
import gui.controllers.customObjects.DateEditingCell;
import gui.controllers.customObjects.TimeSpinnerCell;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Airport;
import models.Flight;
import service.ServiceImpl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.ZoneId;

public class FlightController extends BaseController {

    public FlightController(TableView table) {
        tableView.getItems().clear();
        tableView = table;
        checkSet();
        service = new ServiceImpl<Flight>();
        setDynamicColumn();
        table.setItems(service.findAll(table.getId()));
    }

    @Override
    public void add() {
        Flight flight = new Flight();
        tableView.getItems().add(flight);
        changedList.add(flight);
    }

    @Override
    public Class getEntity() {
        return Flight.class;
    }

    private void setDynamicColumn() {
        tableView.getColumns().clear();
        TableColumn flightNumber = new TableColumn("Flight number");
        flightNumber.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightID"));
        flightNumber.editableProperty().setValue(false);
        TableColumn flightDateColumn = new TableColumn("Date");
        flightDateColumn.setMinWidth(100);
        flightDateColumn.setCellValueFactory(new PropertyValueFactory<Flight, Date>("date"));
        flightDateColumn.setCellFactory(p -> new DateEditingCell<Flight, java.util.Date>());
        flightDateColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, java.util.Date>>) t -> {
            t.getRowValue().setDate(Date.valueOf(t.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            changedList.add(t.getRowValue());
        });
        TableColumn flightDurationColumn = new TableColumn("Duration");
        flightDurationColumn.setMinWidth(100);
        flightDurationColumn.setCellValueFactory(new PropertyValueFactory<Flight, Time>("duration"));
        flightDurationColumn.setCellFactory(p -> new TimeSpinnerCell<Flight, Time>());
        flightDurationColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, LocalTime>>) t -> {
            t.getRowValue().setDuration(Time.valueOf(t.getNewValue()));
            changedList.add(t.getRowValue());
        });
        TableColumn flightDestinationColumn = new TableColumn("Destination");
        flightDestinationColumn.setMinWidth(100);
        flightDestinationColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("destination"));
        flightDestinationColumn.setCellFactory(p -> new ComboBoxCell<Flight, Airport>(new ServiceImpl<Airport>().findAll("Airport")));
        flightDestinationColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDestination(t.getNewValue());
            changedList.add(t.getRowValue());
        });
        TableColumn flightDepartureColumn = new TableColumn("Departure");
        flightDepartureColumn.setMinWidth(100);
        flightDepartureColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("departure"));
        flightDepartureColumn.setCellFactory(p -> new ComboBoxCell<Flight, Airport>(new ServiceImpl<Airport>().findAll("Airport")));
        flightDepartureColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDeparture(t.getNewValue());
            changedList.add(t.getRowValue());
        });
        tableView.getColumns().addAll(flightNumber,flightDestinationColumn, flightDepartureColumn,flightDurationColumn, flightDateColumn);
    }
}
