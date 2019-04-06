package gui.controls;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import models.*;


public class MainWindowController {
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
    private TableView<FlightInfo> flightInfoTable;
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

    private BaseController controller;

    @FXML
    public void initialize() {
        controller = new AirportController(airportTable);
        airportTab.setOnSelectionChanged(event -> controller = new AirportController(airportTable));
        boardTab.setOnSelectionChanged(event -> controller = new BoardController(boardTable));
        clientTab.setOnSelectionChanged(event -> controller = new ClientController(clientTable));
        flightTab.setOnSelectionChanged(event -> controller = new FlightController(flightTable));
        flightInfoTab.setOnSelectionChanged(event -> controller = new FlightInfoController(flightInfoTable));
        ticketTab.setOnSelectionChanged(event -> controller = new TicketController(ticketTable));
        addBtn.setOnAction(event -> controller.add());
        deleteBtn.setOnAction(event -> controller.delete());
        saveTableBtn.setOnAction(event -> controller.saveOrUpdate());
    }

}

