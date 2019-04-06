package gui.controls;

import gui.controls.tableCellComponent.ComboBoxCell;
import gui.controls.tableCellComponent.DateEditingCell;
import gui.controls.tableCellComponent.TimeSpinnerCell;
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

class FlightController extends BaseController {

    private static boolean setColumn = false;

    FlightController(TableView table) {
        tableView.getItems().clear();
        tableView = table;
        checkSet();
        service = new ServiceImpl<Flight>();
        if (!setColumn) {
            tableView.getColumns().addAll(setStaticColumn());
        }
        setDynamicColumn();
        table.setItems(service.findAll(table.getId()));
    }

    @Override
    void add() {
        Flight flight = new Flight();
        tableView.getItems().add(flight);
        changedSet.add(flight);
    }

    private ObservableList setStaticColumn() {
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
        setColumn = true;
        return FXCollections.observableArrayList(flightDurationColumn, flightDateColumn);
    }

    private void setDynamicColumn() {
        if (tableView.getColumns().size() > 3) {
            tableView.getColumns().remove(tableView.getColumns().size() - 1);
            tableView.getColumns().remove(tableView.getColumns().size() - 1);
        }
        TableColumn flightDestinationColumn = new TableColumn("Destination");
        flightDestinationColumn.setMinWidth(100);
        flightDestinationColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("destination"));
        flightDestinationColumn.setCellFactory(p -> new ComboBoxCell<Flight, Airport>(new ServiceImpl<Airport>().findAll(tableView.getId())));
        flightDestinationColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDestination(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        TableColumn flightDepartureColumn = new TableColumn("Departure");
        flightDepartureColumn.setMinWidth(100);
        flightDepartureColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("departure"));
        flightDepartureColumn.setCellFactory(p -> new ComboBoxCell<Flight, Airport>(new ServiceImpl<Airport>().findAll(tableView.getId())));
        flightDepartureColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDeparture(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        tableView.getColumns().addAll(flightDestinationColumn, flightDepartureColumn);

    }
}
