package gui.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.*;
import service.*;

import java.util.HashMap;
import java.util.Map;

public class MainWindowController {
    @FXML private TableView<Airport> airportTable;
    @FXML private TableView<Flight> flightTable;
    @FXML private TableView<Board> boardTable;
    @FXML private TableView<Client> clientTable;
    @FXML private TableView<Ticket> ticketTable;
    @FXML private TableView<FlightInformation> flightInfoTable;
    @FXML private TabPane mainPane;

    private ObservableList<Airport> changedList = FXCollections.observableArrayList();
    private Map<TableView,AbstractService> serviceMap;

    @FXML public void initialize() {
        serviceMap =  new HashMap<TableView,AbstractService>(){{
            put(airportTable, new AirportService());
            put(flightTable, new FlightService());
            put(boardTable, new BoardService());
            put(clientTable, new ClientService());
            put(flightInfoTable, new FlightInformationService());
            put(ticketTable, new TicketService());
        }};
        RefreshAll();
    }
    @FXML private void Delete() {
        Tab selectedTab = mainPane.getSelectionModel().getSelectedItem();
        TableView table = (TableView)selectedTab.getContent().lookup("#"+selectedTab.getId());
        AbstractService service = serviceMap.get(table);
        if(!table.getSelectionModel().isEmpty()) {
            service.Delete(table.getSelectionModel().getSelectedItem());
            table.setItems(service.FindAll());
        }
    }
    @FXML private void RefreshCurr() { }
    @FXML private void Add() { }
    @FXML private void PushToList(TableColumn.CellEditEvent<Airport, String> event){

        Tab selectedTab = mainPane.getSelectionModel().getSelectedItem();
        TableView table = (TableView)selectedTab.getContent().lookup("#"+selectedTab.getId());
        Airport curr = (Airport) event.getTableView().getItems().get(event.getTablePosition().getRow());
        if(changedList.contains(curr))
        {
            changedList.add(curr);
        }
    }

    private void RefreshAll() {
        mainPane.getTabs().forEach(e -> {
            TableView table = (TableView)e.getContent().lookup("#"+e.getId());
            AbstractService service = serviceMap.get(table);
            table.setItems(service.FindAll());
        });
    }
}
