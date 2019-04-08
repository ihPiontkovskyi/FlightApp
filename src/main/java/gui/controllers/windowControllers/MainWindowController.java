package gui.controllers.windowControllers;

import gui.controllers.modelControllers.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import models.*;


public class MainWindowController {
    private static TabPane mainPane_;
    @FXML
    private TableView flightInfoTable;
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
        mainPane_ = mainPane;
        controller = new AirportController(airportTable);
        airportTab.setOnSelectionChanged(event -> controller = new AirportController(airportTable));
        boardTab.setOnSelectionChanged(event -> controller = new BoardController(boardTable));
        clientTab.setOnSelectionChanged(event -> controller = new ClientController(clientTable));
        flightTab.setOnSelectionChanged(event -> controller = new FlightController(flightTable));
        ticketTab.setOnSelectionChanged(event -> controller = new TicketController(ticketTable));
        flightInfoTab.setOnSelectionChanged(event -> controller = new FlightInfoController(flightInfoTable));
        addBtn.setOnAction(event -> controller.add());
        deleteBtn.setOnAction(event -> controller.delete());
        saveTableBtn.setOnAction(event -> controller.saveOrUpdate());
        searchBtn.setOnAction(event -> {
            if (controller.startSearch()) {
                ObservableList foundItems = controller.search();
                controller.getTableView().getItems().clear();
                controller.getTableView().getItems().addAll(foundItems);
                BaseController.clearList();
            }
            return;
        });
        refreshBtn.setOnAction(event -> {
            controller.refresh();
            BaseController.clearList();
        });

    }

    public static String getSelectionModelTypeName() {
        return mainPane_.getSelectionModel().getSelectedItem().getId();
    }
}

