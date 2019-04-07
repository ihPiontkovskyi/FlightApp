package gui.controllers.windowControllers;

import gui.controllers.modelControllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class SearchWindowController {
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField classField;
    @FXML
    private TextField passportField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private DatePicker lastRepairField;
    @FXML
    private TextField freeSeatField;
    @FXML
    private TextField jetTypeField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField codeField;
    @FXML
    private TextField cityField;
    @FXML
    private Button startSearch;
    @FXML
    private DatePicker purchaseField;
    @FXML
    private DatePicker flightDateField;
    @FXML
    private TextField priceField;

    private Map<String, String> resultMap = new HashMap<>();
    private Map<String, TextField> fieldMap;

    @FXML
    public void initialize() {
        setMap();
        startSearch.setOnAction(event -> {
            BaseController.setMap(resultMap);
            ((Node)event.getSource()).getScene().getWindow().hide();
        });
    }

    private void setAction(TextField field, String column) {
        field.textProperty().addListener((observable,oldValue,newValue) -> {
                if (!newValue.trim().equals("")) {
                    resultMap.put(column, newValue);
                } else resultMap.remove(column);

        });
    }
    private void setDateAction(DatePicker picker, String column) {
        picker.setOnAction(t -> {
            if (!resultMap.containsKey(column)) {
                resultMap.put(column, t.toString());
            } else {
                resultMap.replace(column, t.toString());
            }
        });
    }
    private void setMap()
    {
        if(BaseController.getType().equals("Flight")) {
            fieldMap = new HashMap<String, TextField>() {
                {
                    put("boards.tickets.classType", classField);
                    put("boards.clients.passportID", passportField);
                    put("boards.clients.lastName", lastNameField);
                    put("boards.clients.firstName", firstNameField);
                    put("boards.freeSeat", freeSeatField);
                    put("boards.jetType", jetTypeField);
                    put("duration", timeField);
                    put("destination.city", cityField);
                    put("boards.tickets.price", priceField);
                    put("destination.airportCode", codeField);

                }
            };
            fieldMap.forEach((k, v) -> setAction(v, k));
            setDateAction(flightDateField, "date");
            setDateAction(purchaseField, "boards.tickets.datePurchase");
            setDateAction(lastRepairField, "boards.lastRepair");
        }
        if(BaseController.getType().equals("Boards")) {
            fieldMap = new HashMap<String, TextField>() {
                {
                    put("tickets.classType", classField);
                    put("clients.passportID", passportField);
                    put("clients.lastName", lastNameField);
                    put("clients.firstName", firstNameField);
                    put("freeSeat", freeSeatField);
                    put("jetType", jetTypeField);
                    put("flights.duration", timeField);
                    put("flights.destination.city", cityField);
                    put("tickets.price", priceField);
                    put("flights.destination.airportCode", codeField);

                }
            };
            fieldMap.forEach((k, v) -> setAction(v, k));
            setDateAction(flightDateField, "flights.date");
            setDateAction(purchaseField, "tickets.datePurchase");
            setDateAction(lastRepairField, "lastRepair");
        }
        if(BaseController.getType().equals("Ticket")) {
            fieldMap = new HashMap<String, TextField>() {
                {
                    put("classType", classField);
                    put("board.clients.passportID", passportField);
                    put("board.clients.lastName", lastNameField);
                    put("board.clients.firstName", firstNameField);
                    put("board.freeSeat", freeSeatField);
                    put("board.jetType", jetTypeField);
                    put("board.flights.duration", timeField);
                    put("board.flights.destination.city", cityField);
                    put("price", priceField);
                    put("board.flights.destination.airportCode", codeField);

                }
            };
            fieldMap.forEach((k, v) -> setAction(v, k));
            setDateAction(flightDateField, "board.flights.date");
            setDateAction(purchaseField, "datePurchase");
            setDateAction(lastRepairField, "board.lastRepair");
        }
        if(BaseController.getType().equals("Client")) {
            fieldMap = new HashMap<String, TextField>() {
                {
                    put("boards.tickets.classType", classField);
                    put("passportID", passportField);
                    put("lastName", lastNameField);
                    put("firstName", firstNameField);
                    put("boards.freeSeat", freeSeatField);
                    put("boards.jetType", jetTypeField);
                    put("boards.flights.duration", timeField);
                    put("boards.flights.destination.city", cityField);
                    put("boards.tickets.price", priceField);
                    put("boards.flights.destination.airportCode", codeField);

                }
            };
            fieldMap.forEach((k, v) -> setAction(v, k));
            setDateAction(flightDateField, "boards.flights.date");
            setDateAction(purchaseField, "boards.tickets.datePurchase");
            setDateAction(lastRepairField, "boards.lastRepair");
        }
    }
}
