package gui.controls;

import gui.controls.TableCellComponent.DateEditingCell;
import gui.controls.TableCellComponent.EditingCell;
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
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


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
    private TabPane mainPane;
    @FXML
    private Button saveTableBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button addBtn;

    private Map<TableView, Service> serviceMap;


    private Set<BaseModel> changedSet = new HashSet<BaseModel>();

    @FXML
    public void initialize() {
        serviceMap = new HashMap<TableView, Service>() {{
            put(airportTable, new ServiceImpl<Airport>());
            put(flightTable, new ServiceImpl<Flight>());
            put(boardTable, new ServiceImpl<Board>());
            put(clientTable, new ServiceImpl<Client>());
            put(flightInfoTable, new ServiceImpl<FlightInfo>());
            put(ticketTable, new ServiceImpl<Ticket>());
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
            Service service = serviceMap.get(table);
            if (!table.getSelectionModel().isEmpty()) {
                service.delete(table.getSelectionModel().getSelectedItem());
                table.setItems(service.findAll(table.getId()));
            }
        });
        addBtn.setOnAction(e -> {
            Tab selectedTab = mainPane.getSelectionModel().getSelectedItem();
            TableView table = (TableView) selectedTab.getContent().lookup("#" + selectedTab.getId());
            if(table == airportTable)
            {
                airportTable.getItems().add(new Airport());
            }
            if(table == boardTable)
            {
                boardTable.getItems().add(new Board());
            }
            if(table == clientTable)
            {
                clientTable.getItems().add(new Client());
            }
            if(table == flightTable)
            {
                flightTable.getItems().add(new Flight());
            }
            if(table == ticketTable)
            {
                ticketTable.getItems().add(new Ticket());
            }
            if(table == flightInfoTable)
            {
                flightInfoTable.getItems().add(new FlightInfo());
            }
        });
    }

    private void RefreshAll() {
        mainPane.getTabs().forEach(e -> {
            TableView table = (TableView) e.getContent().lookup("#" + e.getId());
            Service service = serviceMap.get(table);
            table.setItems(service.findAll(table.getId()));
        });
    }

    private void setTables() {
        setAirportTable();
        setBoardTable();
        setFlightTable();
        setClientTable();
        setFlightInfoTable();
        setTicketTable();
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
        boardTable.getColumns().addAll(column, column1, column2);
    }
    private void setFlightTable() {
        TableColumn column = createFlightDateColumn();
        TableColumn column1 = createFlightDepartureColumn();
        TableColumn column2 = createFlightDestinationColumn();
        TableColumn column3 = createFlightDurationColumn();
        flightTable.getColumns().addAll(column,column1,column2,column3);
    }*/
    private TableColumn createAirportCodeColumn() {
        TableColumn airportCodeColumn = new TableColumn("Airport code");
        airportCodeColumn.setMinWidth(100);
        airportCodeColumn.setCellValueFactory(new PropertyValueFactory<Airport, String>("airportCode"));
        airportCodeColumn.setCellFactory(p -> new EditingCell<Airport, String>());
        airportCodeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Airport, String>>) t -> {
            t.getRowValue().setAirportCode(t.getNewValue());
            changedSet.add(t.getRowValue());
        });

        return airportCodeColumn;
    }
    private TableColumn createAirportCityColumn() {TableColumn city = new TableColumn("City");
        city.setMinWidth(100);
        city.setCellValueFactory(new PropertyValueFactory<Airport, String>("city"));
        city.setCellFactory(p -> new EditingCell<Airport,String>());
        city.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Airport, String>>) t -> {
            t.getRowValue().setCity(t.getNewValue());
            changedSet.add(t.getRowValue());
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
        boardFreeSeatColumn.setCellValueFactory(new PropertyValueFactory<Board, Integer>("freeSeat"));
        boardFreeSeatColumn.setCellFactory(p -> new EditingCell<Board,Integer>());
        boardFreeSeatColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, Integer>>) t -> {
            t.getRowValue().setFreeSeat(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return boardFreeSeatColumn;
    }
    private TableColumn createBoardJetTypeColumn() {
        TableColumn boardJetTypeColumn = new TableColumn("Jet type");
        boardJetTypeColumn.setMinWidth(100);
        boardJetTypeColumn.setCellValueFactory(new PropertyValueFactory<Board, String>("jetType"));
        boardJetTypeColumn.setCellFactory(p -> new EditingCell<Board,String>());
        boardJetTypeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, String>>) t -> {
            t.getRowValue().setJetType(t.getNewValue());
            changedSet.add(t.getRowValue());
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
        flightDateColumn.setCellValueFactory(new PropertyValueFactory<Flight, Date>("date"));
        flightDateColumn.setCellFactory(p -> new DateEditingCell<Flight, java.util.Date>());
        flightDateColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Date>>) t -> {
            t.getRowValue().setDate(t.getNewValue());
            changedSet.add(t.getRowValue());
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
            changedSet.add(t.getRowValue());
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
            changedSet.add(t.getRowValue());
        });
        return flightDepartureColumn;
    }
*/
}

