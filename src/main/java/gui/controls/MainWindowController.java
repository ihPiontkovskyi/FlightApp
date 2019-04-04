package gui.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.*;
import service.*;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;


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
    private TableView<FlightInformation> flightInfoTable;
    @FXML
    private TabPane mainPane;
    @FXML
    private Button saveTableBtn;
    @FXML
    private Button deleteBtn;

    private Map<TableView, AbstractService> serviceMap;


    private ObservableList<BaseModel> changedList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        serviceMap = new HashMap<TableView, AbstractService>() {{
            put(airportTable, new AirportService());
            put(flightTable, new FlightService());
            put(boardTable, new BoardService());
            put(clientTable, new ClientService());
            put(flightInfoTable, new FlightInformationService());
            put(ticketTable, new TicketService());
        }};
        RefreshAll();
        setTables();
        saveTableBtn.setOnAction(e -> {
            serviceMap.get(airportTable).Update(changedList);
            changedList.clear();
        });
        deleteBtn.setOnAction(e -> {
            Tab selectedTab = mainPane.getSelectionModel().getSelectedItem();
            TableView table = (TableView) selectedTab.getContent().lookup("#" + selectedTab.getId());
            AbstractService service = serviceMap.get(table);
            if (!table.getSelectionModel().isEmpty()) {
                service.Delete(table.getSelectionModel().getSelectedItem());
                table.setItems(service.FindAll());
            }
        });
    }
    private void RefreshAll() {
        mainPane.getTabs().forEach(e -> {
            TableView table = (TableView) e.getContent().lookup("#" + e.getId());
            AbstractService service = serviceMap.get(table);
            table.setItems(service.FindAll());
        });
    }

    private  void setTables(){
        setAirportTable();
        setBoardTable();
        //setFlightTable();
    }
    private void setAirportTable() {
        TableColumn column = createAirportCityColumn();
        TableColumn column1 = createAirportCodeColumn();
        airportTable.getColumns().addAll(column, column1);
    }
    private void setBoardTable() {
        TableColumn column = createBoardLastRepairColumn();
        TableColumn column1 = createBoardJetTypeColumn();
        TableColumn column2 = createBoardFreeSeatColumn();
        boardTable.getColumns().addAll(column,column1,column2);
    }
    /*private void setFlightTable() {
        TableColumn column = createFlightDateColumn();
        TableColumn column1 = createFlightDepartureColumn();
        TableColumn column2 = createFlightDestinationColumn();
        TableColumn column3 = createFlightDurationColumn();
        flightTable.getColumns().addAll(column,column1,column2,column3);
    }*/
    private TableColumn createAirportCodeColumn() {
        TableColumn airportCodeColumn = new TableColumn("Airport code");
        airportCodeColumn.setMinWidth(100);
        airportCodeColumn.setCellValueFactory(new PropertyValueFactory<Airport, String>("airport_code"));
        airportCodeColumn.setCellFactory(p -> new EditingCell<Airport, String>());
        airportCodeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Airport, String>>) t -> {
            t.getRowValue().setAirport_code(t.getNewValue());
            changedList.add(t.getRowValue());
        });

        return airportCodeColumn;
    }
    private TableColumn createAirportCityColumn() {TableColumn city = new TableColumn("City");
        city.setMinWidth(100);
        city.setCellValueFactory(new PropertyValueFactory<AirportService, String>("city"));
        city.setCellFactory(p -> new EditingCell<Airport,String>());
        city.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Airport, String>>) t -> {
            t.getRowValue().setCity(t.getNewValue());
            changedList.add(t.getRowValue());
        });

        return city;
    }

    private TableColumn createBoardLastRepairColumn() {

        TableColumn boardLastRepairColumn = new TableColumn("Last repair");
        boardLastRepairColumn.setMinWidth(100);
        boardLastRepairColumn.setCellValueFactory(new PropertyValueFactory<Board, Date>("last_repair"));
        boardLastRepairColumn.setCellFactory(p -> new DateEditingCell<Board>());

        boardLastRepairColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, Date>>) t -> {
            t.getRowValue().setLast_repair(t.getNewValue());
            changedList.add(t.getRowValue());
        });

        return boardLastRepairColumn;
    }
    private TableColumn createBoardFreeSeatColumn() {

        TableColumn boardFreeSeatColumn = new TableColumn("Free seat");
        boardFreeSeatColumn.setMinWidth(100);
        boardFreeSeatColumn.setCellValueFactory(new PropertyValueFactory<Board, Integer>("available_seat"));
        boardFreeSeatColumn.setCellFactory(p -> new EditingCell<Board,Integer>());
        boardFreeSeatColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, Integer>>) t -> {
            t.getRowValue().setAvailable_seat(t.getNewValue());
            changedList.add(t.getRowValue());
        });
        return boardFreeSeatColumn;
    }
    private TableColumn createBoardJetTypeColumn() {
        TableColumn boardJetTypeColumn = new TableColumn("Jet type");
        boardJetTypeColumn.setMinWidth(100);
        boardJetTypeColumn.setCellValueFactory(new PropertyValueFactory<Board, String>("jet_type"));
        boardJetTypeColumn.setCellFactory(p -> new EditingCell<Board,String>());
        boardJetTypeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, String>>) t -> {
            t.getRowValue().setJet_type(t.getNewValue());
            changedList.add(t.getRowValue());
        });
        return boardJetTypeColumn;
    }
/*
    private TableColumn createFlightDestinationColumn(){
        TableColumn flightDestinationColumn = new TableColumn("Destination");
        flightDestinationColumn.setMinWidth(100);
        flightDestinationColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("destination"));
        flightDestinationColumn.setCellFactory(p -> new ComboBoxCell<Flight,Airport>(serviceMap.get(airportTable).FindAll()));
        flightDestinationColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDestination(t.getNewValue());
            changedList.add(t.getRowValue());
        });
        return flightDestinationColumn;
    }
    private TableColumn createFlightDateColumn(){
        TableColumn flightDateColumn = new TableColumn("Date");
        flightDateColumn.setMinWidth(100);
        flightDateColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("date"));
        flightDateColumn.setCellFactory(p -> new DateEditingCell<Flight>());
        flightDateColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Date>>) t -> {
            t.getRowValue().setDate(t.getNewValue());
            changedList.add(t.getRowValue());
        });
        return flightDateColumn;
    }
    private TableColumn createFlightDepartureColumn(){
        TableColumn flightDepartureColumn = new TableColumn("Departure");
        flightDepartureColumn.setMinWidth(100);
        flightDepartureColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("departure"));
        flightDepartureColumn.setCellFactory(p -> new ComboBoxCell<Flight,Airport>(serviceMap.get(airportTable).FindAll()));
        flightDepartureColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDeparture(t.getNewValue());
            changedList.add(t.getRowValue());
        });
        return flightDepartureColumn;
    }
    private TableColumn createFlightDurationColumn() {
        TableColumn flightDepartureColumn = new TableColumn("Duration");
        flightDepartureColumn.setMinWidth(100);
        flightDepartureColumn.setCellValueFactory(new PropertyValueFactory<Flight, String >("duration"));
        flightDepartureColumn.setCellFactory(p -> new EditingCell<Flight,String>());
        flightDepartureColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, String>>) t -> {
            t.getRowValue().setDuration(Time.valueOf(t.getNewValue()));
            changedList.add(t.getRowValue());
        });
        return flightDepartureColumn;
    }
*/
}

