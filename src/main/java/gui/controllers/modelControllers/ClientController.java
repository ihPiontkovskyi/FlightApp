package gui.controllers.modelControllers;

import gui.controllers.tableCellComponent.EditingCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Client;
import service.ServiceImpl;

public class ClientController extends BaseController {

    private static boolean setColumn = false;

    public ClientController(TableView table) {
        tableView.getItems().clear();
        tableView = table;
        checkSet();
        service = new ServiceImpl<Client>();
        if (!setColumn) {
            tableView.getColumns().addAll(setStaticColumn());
        }
        table.setItems(service.findAll(table.getId()));
    }

    @Override
    public void add() {
        Client client = new Client();
        tableView.getItems().add(client);
        changedSet.add(client);
    }

    private ObservableList setStaticColumn() {
        TableColumn clientFirstNameColumn = new TableColumn("First name");
        clientFirstNameColumn.setMinWidth(100);
        clientFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("firstName"));
        clientFirstNameColumn.setCellFactory(p -> new EditingCell<Client, String>());
        clientFirstNameColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Client, String>>) t -> {
            t.getRowValue().setFirstName(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        TableColumn clientLastNameColumn = new TableColumn("Last name");
        clientLastNameColumn.setMinWidth(100);
        clientLastNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("lastName"));
        clientLastNameColumn.setCellFactory(p -> new EditingCell<Client, String>());
        clientLastNameColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Client, String>>) t -> {
            t.getRowValue().setLastName(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        TableColumn clientPassportIDColumn = new TableColumn("Passport ID");
        clientPassportIDColumn.setMinWidth(100);
        clientPassportIDColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("passportID"));
        clientPassportIDColumn.setCellFactory(p -> new EditingCell<Client, String>());
        clientPassportIDColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Client, String>>) t -> {
            t.getRowValue().setPassportID(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        setColumn = true;
        return FXCollections.observableArrayList(clientFirstNameColumn, clientLastNameColumn, clientPassportIDColumn);
    }

}
