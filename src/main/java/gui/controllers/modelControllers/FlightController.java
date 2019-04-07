package gui.controllers.modelControllers;

import gui.controllers.tableCellComponent.ComboBoxCell;
import gui.controllers.tableCellComponent.DateEditingCell;
import gui.controllers.tableCellComponent.TimeSpinnerCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        changedSet.add(flight);
    }

    @Override
    public Class getEntity() {
        return Flight.class;
    }

    private void setDynamicColumn() {
        tableView.getColumns().clear();
        TableColumn flightDateColumn = new TableColumn("Date");
        flightDateColumn.setMinWidth(100);
        flightDateColumn.setCellValueFactory(new PropertyValueFactory<Flight, Date>("date"));
        flightDateColumn.setCellFactory(p -> new DateEditingCell<Flight, java.util.Date>());
        flightDateColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, java.util.Date>>) t -> {
            t.getRowValue().setDate(Date.valueOf(t.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            changedSet.add(t.getRowValue());
        });
        TableColumn flightDurationColumn = new TableColumn("Duration");
        flightDurationColumn.setMinWidth(100);
        flightDurationColumn.setCellValueFactory(new PropertyValueFactory<Flight, Time>("duration"));
        flightDurationColumn.setCellFactory(p -> new TimeSpinnerCell<Flight, Time>());
        flightDurationColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, LocalTime>>) t -> {
            t.getRowValue().setDuration(Time.valueOf(t.getNewValue()));
            changedSet.add(t.getRowValue());
        });
        TableColumn flightDestinationColumn = new TableColumn("Destination");
        flightDestinationColumn.setMinWidth(100);
        flightDestinationColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("destination"));
        flightDestinationColumn.setCellFactory(p -> new ComboBoxCell<Flight, Airport>(new ServiceImpl<Airport>().findAll("Airport")));
        flightDestinationColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDestination(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        TableColumn flightDepartureColumn = new TableColumn("Departure");
        flightDepartureColumn.setMinWidth(100);
        flightDepartureColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("departure"));
        flightDepartureColumn.setCellFactory(p -> new ComboBoxCell<Flight, Airport>(new ServiceImpl<Airport>().findAll("Airport")));
        flightDepartureColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDeparture(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        tableView.getColumns().addAll(flightDestinationColumn, flightDepartureColumn,flightDurationColumn, flightDateColumn);
    }
}
