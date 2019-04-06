package gui.controls;

import gui.controls.tableCellComponent.EditingCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Airport;
import service.ServiceImpl;

public class AirportController extends BaseController {
    private static boolean setColumn = false;

    AirportController(TableView table) {
        tableView.getItems().clear();
        tableView = table;
        checkSet();
        service = new ServiceImpl<Airport>();
        if (!setColumn) {
            tableView.getColumns().addAll(setStaticColumn());
        }
        table.setItems(service.findAll(table.getId()));
    }

    @Override
    public void add() {
        Airport airport = new Airport();
        tableView.getItems().add(airport);
        changedSet.add(airport);
    }

    private ObservableList setStaticColumn() {
        TableColumn airportCodeColumn = new TableColumn("Airport code");
        airportCodeColumn.setMinWidth(120);
        airportCodeColumn.setCellValueFactory(new PropertyValueFactory<Airport, String>("airportCode"));
        airportCodeColumn.setCellFactory(p -> new EditingCell<Airport, String>());
        airportCodeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Airport, String>>) t -> {
            t.getRowValue().setAirportCode(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        TableColumn city = new TableColumn("City");
        city.setMinWidth(100);
        city.setCellValueFactory(new PropertyValueFactory<Airport, String>("city"));
        city.setCellFactory(p -> new EditingCell<Airport, String>());
        city.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Airport, String>>) t -> {
            t.getRowValue().setCity(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        setColumn = true;
        return FXCollections.observableArrayList(airportCodeColumn, city);
    }
}
