package gui.controllers.modelControllers;

import gui.controllers.tableCellComponent.ComboBoxCell;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Board;
import models.Flight;
import models.FlightInfo;
import service.ServiceImpl;

public class FlightInfoController extends BaseController {

    public FlightInfoController(TableView table) {
        tableView.getItems().clear();
        tableView = table;
        checkSet();
        service = new ServiceImpl<FlightInfo>();
        setDynamicColumn();
        table.setItems(service.findAll(table.getId()));
    }

    @Override
    public void add() {
        FlightInfo flightInfo = new FlightInfo();
        tableView.getItems().add(flightInfo);
        changedSet.add(flightInfo);
    }

    @Override
    public Class getEntity() {
        return FlightInfo.class;
    }

    private void setDynamicColumn() {
        tableView.getColumns().clear();
        TableColumn flightInfoFlightColumn = new TableColumn("Flight");
        flightInfoFlightColumn.setMinWidth(120);
        flightInfoFlightColumn.setCellValueFactory(new PropertyValueFactory<FlightInfo, Flight>("flight"));
        flightInfoFlightColumn.setCellFactory(p -> new ComboBoxCell<FlightInfo, Flight>(new ServiceImpl<Flight>().findAll("Flight")));
        flightInfoFlightColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<FlightInfo, Flight>>) t -> {
            t.getRowValue().setFlight(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        TableColumn flightInfoBoardColumn = new TableColumn("Board");
        flightInfoBoardColumn.setMinWidth(100);
        flightInfoBoardColumn.setCellValueFactory(new PropertyValueFactory<FlightInfo, Board>("board"));
        flightInfoBoardColumn.setCellFactory(p -> new ComboBoxCell<Flight, Board>(new ServiceImpl<Flight>().findAll("Board")));
        flightInfoBoardColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<FlightInfo, Board>>) t -> {
            t.getRowValue().setBoard(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        tableView.getColumns().addAll(flightInfoBoardColumn, flightInfoFlightColumn);
    }
}