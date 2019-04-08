package gui.controllers.modelControllers;

import gui.controllers.tableCellComponent.EditingCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Airport;
import service.ServiceImpl;

public class AirportController extends BaseController {

    public AirportController(TableView table) {
        tableView.getItems().clear();
        tableView = table;
        checkSet();
        service = new ServiceImpl<Airport>();
        setDynamicColumn();
        table.setItems(service.findAll(table.getId()));
    }

    @Override
    public void add() {
        Airport airport = new Airport();
        tableView.getItems().add(airport);
        changedList.add(airport);
    }

    private void setDynamicColumn() {
        tableView.getColumns().clear();
        TableColumn airportCodeColumn = new TableColumn("Airport code");
        airportCodeColumn.setMinWidth(120);
        airportCodeColumn.setCellValueFactory(new PropertyValueFactory<Airport, String>("airportCode"));
        airportCodeColumn.setCellFactory(p -> new EditingCell<Airport, String>());
        airportCodeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Airport, String>>) t -> {
            t.getRowValue().setAirportCode(t.getNewValue());
            changedList.add(t.getRowValue());
        });
        TableColumn city = new TableColumn("City");
        city.setMinWidth(100);
        city.setCellValueFactory(new PropertyValueFactory<Airport, String>("city"));
        city.setCellFactory(p -> new EditingCell<Airport, String>());
        city.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Airport, String>>) t -> {
            t.getRowValue().setCity(t.getNewValue());
            changedList.add(t.getRowValue());
        });
       tableView.getColumns().addAll(airportCodeColumn, city);
    }
}
