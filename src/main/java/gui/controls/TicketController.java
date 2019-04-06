package gui.controls;

import gui.controls.tableCellComponent.ComboBoxCell;
import gui.controls.tableCellComponent.DateEditingCell;
import gui.controls.tableCellComponent.EditingCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Board;
import models.Client;
import models.Ticket;
import service.ServiceImpl;

import java.sql.Date;
import java.time.ZoneId;

class TicketController extends BaseController {

    private static boolean setColumn = false;
    private final ObservableList<String> classes = FXCollections.observableArrayList("Standard","Economy", "Busyness");

    TicketController(TableView table) {
        tableView.getItems().clear();
        tableView = table;
        checkSet();
        service = new ServiceImpl<Ticket>();
        if (!setColumn) {
            tableView.getColumns().addAll(setStaticColumn());
        }
        setDynamicColumn();
        table.setItems(service.findAll(table.getId()));
    }

    @Override
    void add() {
        Ticket ticket = new Ticket();
        tableView.getItems().add(ticket);
        changedSet.add(ticket);
    }

    private ObservableList setStaticColumn() {
        TableColumn ticketDatePurchaseColumn = new TableColumn("Date purchase");
        ticketDatePurchaseColumn.setMinWidth(100);
        ticketDatePurchaseColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Date>("datePurchase"));
        ticketDatePurchaseColumn.setCellFactory(p -> new DateEditingCell<Ticket, Date>());
        ticketDatePurchaseColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, java.util.Date>>) t -> {
            t.getRowValue().setDatePurchase(Date.valueOf(t.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            changedSet.add(t.getRowValue());
        });
        TableColumn ticketClassTypeColumn = new TableColumn("Class type");
        ticketClassTypeColumn.setMinWidth(100);
        ticketClassTypeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("classType"));
        ticketClassTypeColumn.setCellFactory(p -> new ComboBoxCell<Ticket,String>(classes));
        ticketClassTypeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, String>>) t -> {
            t.getRowValue().setClassType(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        TableColumn ticketPriceColumn = new TableColumn("Price");
        ticketPriceColumn.setMinWidth(100);
        ticketPriceColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("price"));
        ticketPriceColumn.setCellFactory(p -> new EditingCell<Ticket, String>());
        ticketPriceColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, String>>) t -> {
            try {
                Double value = Double.parseDouble(t.getNewValue());
                if (value == null || value < 0) {
                    Alert alert_ = new Alert(Alert.AlertType.ERROR, "An exception occurred!");
                    alert_.setHeaderText("Error");
                    alert_.showAndWait();
                    t.getRowValue().setPrice(0);
                    tableView.refresh();
                } else {
                    t.getRowValue().setPrice(Double.parseDouble(t.getNewValue()));
                    changedSet.add(t.getRowValue());
                }
            } catch (Exception ex) {
                Alert alert_ = new Alert(Alert.AlertType.ERROR, "An exception occurred!");
                alert_.setHeaderText("Error");
                alert_.showAndWait();
                t.getRowValue().setPrice(0);
                tableView.refresh();
            }
        });
        setColumn = true;
        return FXCollections.observableArrayList(ticketPriceColumn, ticketDatePurchaseColumn, ticketClassTypeColumn);
    }

    private void setDynamicColumn() {
        if (tableView.getColumns().size() > 3) {
            tableView.getColumns().remove(tableView.getColumns().size() - 1);
            tableView.getColumns().remove(tableView.getColumns().size() - 1);
        }
        TableColumn ticketClientColumn = new TableColumn("Client");
        ticketClientColumn.setMinWidth(100);
        ticketClientColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Client>("client"));
        ticketClientColumn.setCellFactory(p -> new ComboBoxCell<Ticket, Client>(new ServiceImpl<Client>().findAll("Client")));
        ticketClientColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, Client>>) t -> {
            t.getRowValue().setClient(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        TableColumn ticketBoardColumn = new TableColumn("Board");
        ticketBoardColumn.setMinWidth(100);
        ticketBoardColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Board>("board"));
        ticketBoardColumn.setCellFactory(p -> new ComboBoxCell<Ticket, Board>(new ServiceImpl<Board>().findAll("Board")));
        ticketBoardColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Ticket, Board>>) t -> {
            t.getRowValue().setBoard(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        tableView.getColumns().addAll(ticketClientColumn, ticketBoardColumn);

    }
}
