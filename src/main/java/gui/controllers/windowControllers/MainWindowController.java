package gui.controllers.windowControllers;

import gui.controllers.modelControllers.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.*;


public class MainWindowController {
    @FXML
    private TabPane mainPane;
    @FXML
    private TableView<Airport> airportTable;
    @FXML
    private TableView<Flight> flightTable;
    @FXML
    private TableView<Board> boardTable;
    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private Button saveTableBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Tab flightTab;
    @FXML
    private Tab airportTab;
    @FXML
    private Tab boardTab;
    @FXML
    private Tab ticketTab;
    @FXML
    private Tab flightInfoTab;
    @FXML
    private Tab clientTab;
    @FXML
    private Button searchBtn;
    @FXML
    private Button refreshBtn;

    private BaseController controller;

    @FXML
    public void initialize() {
        controller = new AirportController(airportTable);
        airportTab.setOnSelectionChanged(event -> controller = new AirportController(airportTable));
        boardTab.setOnSelectionChanged(event -> controller = new BoardController(boardTable));
        clientTab.setOnSelectionChanged(event -> controller = new ClientController(clientTable));
        flightTab.setOnSelectionChanged(event -> controller = new FlightController(flightTable));
        ticketTab.setOnSelectionChanged(event -> controller = new TicketController(ticketTable));
        addBtn.setOnAction(event -> controller.add());
        deleteBtn.setOnAction(event -> controller.delete());
        saveTableBtn.setOnAction(event -> controller.saveOrUpdate());
        searchBtn.setOnAction(event -> {
            controller.startSearch();
            ObservableList foundItems = controller.search();
            if(foundItems.size() > 0)
            {
                controller.getTableView().getItems().clear();
                controller.getTableView().getItems().addAll(foundItems);
            }
        });
        refreshBtn.setOnAction(event-> controller.refresh());

    }

}

