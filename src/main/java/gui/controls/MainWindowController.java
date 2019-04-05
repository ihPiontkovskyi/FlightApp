package gui.controls;

import gui.controls.tableCellComponent.ComboBoxCell;
import gui.controls.tableCellComponent.DateEditingCell;
import gui.controls.tableCellComponent.EditingCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
import service.*;

import java.sql.Date;
import java.sql.Time;
import java.time.ZoneId;
import java.util.*;


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
            TableView table = (TableView) mainPane.getSelectionModel().getSelectedItem().getContent().lookup("#" + mainPane.getSelectionModel().getSelectedItem().getId());
            serviceMap.get(table).saveOrUpdate(changedSet);
            changedSet.clear();
        });
        deleteBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?!");
            alert.setHeaderText("Warning");
            ButtonType buttonTypeDelete = new ButtonType("Delete");
            ButtonType buttonTypeCancel = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(buttonTypeDelete, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeDelete) {
                Tab selectedTab = mainPane.getSelectionModel().getSelectedItem();
                TableView table = (TableView) selectedTab.getContent().lookup("#" + selectedTab.getId());
                Service service = serviceMap.get(table);
                if (!table.getSelectionModel().isEmpty()) {
                    service.delete(table.getSelectionModel().getSelectedItem());
                    table.setItems(service.findAll(table.getId()));
                }
                else
                {
                    Alert alert_ = new Alert(Alert.AlertType.ERROR, "An exception occurred!");
                    alert_.setHeaderText("Error");
                    alert_.showAndWait();
                }
            }
        });
        addBtn.setOnAction(e -> {
            Tab selectedTab = mainPane.getSelectionModel().getSelectedItem();
            TableView table = (TableView) selectedTab.getContent().lookup("#" + selectedTab.getId());
            if (table == airportTable) {
                airportTable.getItems().add(new Airport());
            }
            if (table == boardTable) {
                boardTable.getItems().add(new Board());
            }
            if (table == clientTable) {
                clientTable.getItems().add(new Client());
            }
            if (table == flightTable) {
                flightTable.getItems().add(new Flight());
            }
            if (table == ticketTable) {
                ticketTable.getItems().add(new Ticket());
            }
            if (table == flightInfoTable) {
                flightInfoTable.getItems().add(new FlightInfo());
            }
        });
        //add alert for changing tabs!
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
        flightTable.getColumns().addAll(column, column1, column2, column3);
    }

    private void setClientTable() {
        TableColumn column = createClientFirstNameColumn();
        TableColumn column1 = createClientLastNameColumn();
        TableColumn column2 = createClientPassportIDColumn();
        clientTable.getColumns().addAll(column, column1, column2);
    }

    private void setFlightInfoTable() {
        TableColumn column = createFlightInfoBoardColumn();
        TableColumn column1 = createFlightInfoFlightColumn();
        flightInfoTable.getColumns().addAll(column, column1);
    }

    private void setTicketTable() {
        TableColumn column = createTicketBoardColumn();
        TableColumn column1 = createTicketClientColumn();
        TableColumn column2 = createTicketPriceColumn();
        TableColumn column3 = createTicketTimePurchaseColumn();
        TableColumn column4 = createTicketClassTypeColumn();
        ticketTable.getColumns().addAll(column, column1, column2, column3, column4);
    }

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

    private TableColumn createAirportCityColumn() {
        TableColumn city = new TableColumn("City");
        city.setMinWidth(100);
        city.setCellValueFactory(new PropertyValueFactory<Airport, String>("city"));
        city.setCellFactory(p -> new EditingCell<Airport, String>());
        city.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Airport, String>>) t -> {
            t.getRowValue().setCity(t.getNewValue());
            changedSet.add(t.getRowValue());
        });

        return city;
    }

    private TableColumn createBoardLastRepairColumn() {

        TableColumn boardLastRepairColumn = new TableColumn("Last repair");
        boardLastRepairColumn.setMinWidth(100);
        boardLastRepairColumn.setCellValueFactory(new PropertyValueFactory<Board, Date>("lastRepair"));
        boardLastRepairColumn.setCellFactory(p -> new DateEditingCell<Board, java.util.Date>());
        boardLastRepairColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, java.util.Date>>) t -> {
            t.getRowValue().setLastRepair(Date.valueOf(t.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            changedSet.add(t.getRowValue());
        });

        return boardLastRepairColumn;
    }

    private TableColumn createBoardFreeSeatColumn() {

        TableColumn boardFreeSeatColumn = new TableColumn("Free seat");
        boardFreeSeatColumn.setMinWidth(100);
        boardFreeSeatColumn.setCellValueFactory(new PropertyValueFactory<Board, String>("freeSeat"));
        boardFreeSeatColumn.setCellFactory(p -> new EditingCell<Board, String>());
        boardFreeSeatColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, String>>) t -> {
            //check positive int
            t.getRowValue().setFreeSeat(Integer.parseInt(t.getNewValue()));
            changedSet.add(t.getRowValue());
        });
        return boardFreeSeatColumn;
    }

    private TableColumn createBoardJetTypeColumn() {
        TableColumn boardJetTypeColumn = new TableColumn("Jet type");
        boardJetTypeColumn.setMinWidth(100);
        boardJetTypeColumn.setCellValueFactory(new PropertyValueFactory<Board, String>("jetType"));
        boardJetTypeColumn.setCellFactory(p -> new EditingCell<Board, String>());
        boardJetTypeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, String>>) t -> {
            t.getRowValue().setJetType(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return boardJetTypeColumn;
    }

    private TableColumn createFlightDestinationColumn() {
        TableColumn flightDestinationColumn = new TableColumn("Destination");
        flightDestinationColumn.setMinWidth(100);
        flightDestinationColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("destination"));
        flightDestinationColumn.setCellFactory(p -> new ComboBoxCell<Flight, Airport>(airportTable.getItems()));
        flightDestinationColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDestination(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return flightDestinationColumn;
    }

    private TableColumn createFlightDateColumn() {
        TableColumn flightDateColumn = new TableColumn("Date");
        flightDateColumn.setMinWidth(100);
        flightDateColumn.setCellValueFactory(new PropertyValueFactory<Flight, Date>("date"));
        flightDateColumn.setCellFactory(p -> new DateEditingCell<Flight, java.util.Date>());
        flightDateColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, java.util.Date>>) t -> {
            t.getRowValue().setDate(Date.valueOf(t.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            changedSet.add(t.getRowValue());
        });
        return flightDateColumn;
    }

    private TableColumn createFlightDepartureColumn() {
        TableColumn flightDepartureColumn = new TableColumn("Departure");
        flightDepartureColumn.setMinWidth(100);
        flightDepartureColumn.setCellValueFactory(new PropertyValueFactory<Flight, Airport>("departure"));
        flightDepartureColumn.setCellFactory(p -> new ComboBoxCell<Flight, Airport>(airportTable.getItems()));
        flightDepartureColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, Airport>>) t -> {
            t.getRowValue().setDeparture(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return flightDepartureColumn;
    }

    private TableColumn createFlightDurationColumn() {
        TableColumn flightDepartureColumn = new TableColumn("Duration");
        flightDepartureColumn.setMinWidth(100);
        flightDepartureColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("duration"));
        flightDepartureColumn.setCellFactory(p -> new EditingCell<Flight, String>());
        flightDepartureColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Flight, String>>) t -> {
            t.getRowValue().setDuration(Time.valueOf(t.getNewValue()));
            changedSet.add(t.getRowValue());
        });
        return flightDepartureColumn;
    }

    private TableColumn createClientFirstNameColumn() {
        TableColumn clientFirstNameColumn = new TableColumn("First name");
        clientFirstNameColumn.setMinWidth(100);
        clientFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("firstName"));
        clientFirstNameColumn.setCellFactory(p -> new EditingCell<Client, String>());
        clientFirstNameColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Client, String>>) t -> {
            t.getRowValue().setFirstName(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return clientFirstNameColumn;
    }

    private TableColumn createClientLastNameColumn() {
        TableColumn clientLastNameColumn = new TableColumn("Last name");
        clientLastNameColumn.setMinWidth(100);
        clientLastNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("lastName"));
        clientLastNameColumn.setCellFactory(p -> new EditingCell<Client, String>());
        clientLastNameColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Client, String>>) t -> {
            t.getRowValue().setLastName(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return clientLastNameColumn;
    }

    private TableColumn createClientPassportIDColumn() {
        TableColumn clientPassportIDColumn = new TableColumn("Passport ID");
        clientPassportIDColumn.setMinWidth(100);
        clientPassportIDColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("passportID"));
        clientPassportIDColumn.setCellFactory(p -> new EditingCell<Client, String>());
        clientPassportIDColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Client, String>>) t -> {
            t.getRowValue().setPassportID(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return clientPassportIDColumn;
    }

    private TableColumn createFlightInfoFlightColumn() {
        TableColumn flightInfoFlightColumn = new TableColumn("Flight");
        flightInfoFlightColumn.setMinWidth(120);
        flightInfoFlightColumn.setCellValueFactory(new PropertyValueFactory<FlightInfo, Flight>("flight"));
        flightInfoFlightColumn.setCellFactory(p -> new ComboBoxCell<FlightInfo, Flight>(flightTable.getItems()));
        flightInfoFlightColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<FlightInfo, Flight>>) t -> {
            t.getRowValue().setFlight(t.getNewValue());
            changedSet.add(t.getRowValue());
        });

        return flightInfoFlightColumn;
    }

    private TableColumn createFlightInfoBoardColumn() {
        TableColumn flightInfoBoardColumn = new TableColumn("Board");
        flightInfoBoardColumn.setMinWidth(100);
        flightInfoBoardColumn.setCellValueFactory(new PropertyValueFactory<FlightInfo, Board>("board"));
        flightInfoBoardColumn.setCellFactory(p -> new ComboBoxCell<Flight, Board>(boardTable.getItems()));
        flightInfoBoardColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<FlightInfo, Board>>) t -> {
            t.getRowValue().setBoard(t.getNewValue());
            changedSet.add(t.getRowValue());
        });

        return flightInfoBoardColumn;
    }

    private TableColumn createTicketPriceColumn() {
        TableColumn ticketPriceColumn = new TableColumn("Price");
        ticketPriceColumn.setMinWidth(100);
        ticketPriceColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("price"));
        ticketPriceColumn.setCellFactory(p -> new EditingCell<Ticket, String>());
        ticketPriceColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, String>>) t -> {
            Double value = Double.parseDouble(t.getNewValue());
            //check positive int
            t.getRowValue().setPrice(value);
            changedSet.add(t.getRowValue());
        });
        return ticketPriceColumn;
    }

    private TableColumn createTicketTimePurchaseColumn() {
        TableColumn ticketTimePurchaseColumn = new TableColumn("Date purchase");
        ticketTimePurchaseColumn.setMinWidth(100);
        ticketTimePurchaseColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Date>("datePurchase"));
        ticketTimePurchaseColumn.setCellFactory(p -> new DateEditingCell<Ticket,Date>());
        ticketTimePurchaseColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, java.util.Date>>) t -> {
            t.getRowValue().setDatePurchase(Date.valueOf(t.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            changedSet.add(t.getRowValue());
        });
        return ticketTimePurchaseColumn;
    }

    private TableColumn createTicketClassTypeColumn() {
        TableColumn ticketClassTypeColumn = new TableColumn("Class type");
        ticketClassTypeColumn.setMinWidth(100);
        ticketClassTypeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("classType"));
        ticketClassTypeColumn.setCellFactory(p -> new EditingCell<Ticket, String>());
        ticketClassTypeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, String>>) t -> {
            t.getRowValue().setClassType(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return ticketClassTypeColumn;
    }

    private TableColumn createTicketClientColumn() {
        TableColumn ticketClientColumn = new TableColumn("Client");
        ticketClientColumn.setMinWidth(100);
        ticketClientColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Client>("client"));
        ticketClientColumn.setCellFactory(p -> new ComboBoxCell<Ticket, Client>(clientTable.getItems()));
        ticketClientColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, Client>>) t -> {
            t.getRowValue().setClient(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return ticketClientColumn;
    }

    private TableColumn createTicketBoardColumn() {
        TableColumn ticketBoardColumn = new TableColumn("Board");
        ticketBoardColumn.setMinWidth(100);
        ticketBoardColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Board>("board"));
        ticketBoardColumn.setCellFactory(p -> new ComboBoxCell<Ticket, Client>(boardTable.getItems()));
        ticketBoardColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, Board>>) t -> {
            t.getRowValue().setBoard(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        return ticketBoardColumn;
    }

}

