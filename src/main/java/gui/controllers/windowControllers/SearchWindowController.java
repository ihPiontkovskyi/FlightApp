package gui.controllers.windowControllers;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import gui.controllers.modelControllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchWindowController {
    private DateCell iniCell=null;
    private DateCell endCell=null;
    private LocalDate iniDate;
    private LocalDate endDate;
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
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
    private ComboBox classField;
    @FXML
    private TextField timeField;
    @FXML
    private Button startSearch;
    @FXML
    private DatePicker purchaseField;
    @FXML
    private DatePicker flightDateField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField destinationFieldCode;
    @FXML
    private TextField destinationFieldCity;
    @FXML
    private TextField departureFieldCode;
    @FXML
    private TextField departureFieldCity;

    private Map<String, String> resultMap = new HashMap<>();
    private Map<String, TextField> fieldMap;

    @FXML
    public void initialize() {
        setMap();
        classField.getItems().addAll("Economy","Standard","Business");
        startSearch.setOnAction(event -> {
            BaseController.setMap(resultMap);
            ((Node) event.getSource()).getScene().getWindow().hide();
        });
        classField.setOnAction(event -> {
            if (MainWindowController.getSelectionModelTypeName().equals("Flight")) {
                resultMap.put("fromFlightToBoard.tickets.classType", classField.getValue().toString());
            }
            if (MainWindowController.getSelectionModelTypeName().equals("Board")) {
               resultMap.put("tickets.classType", classField.getValue().toString());
            }
            if (MainWindowController.getSelectionModelTypeName().equals("Client")) {
                resultMap.put("tickets.classType", classField.getValue().toString());
            }
            if (MainWindowController.getSelectionModelTypeName().equals("Ticket")) {
                resultMap.put("tickets.classType", classField.getValue().toString());
            }
        });
    }

    private void setAction(TextField field, String column) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().equals("")) {
                resultMap.put(column, newValue);
            } else resultMap.remove(column);

        });
    }

    private void setDateAction(DatePicker datePicker, String column) {
        datePicker.setConverter(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate object) {
                if(iniDate!=null && endDate!=null){
                    return iniDate.format(formatter)+" - "+endDate.format(formatter);
                }
                return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if(string.contains("-")){
                    try{
                        iniDate=LocalDate.parse(string.split("-")[0].trim(), formatter);
                        endDate=LocalDate.parse(string.split("-")[1].trim(), formatter);
                    } catch(DateTimeParseException dte){
                        return LocalDate.parse(string, formatter);
                    }
                    return iniDate;
                }
                return LocalDate.parse(string, formatter);
            }
        });
        datePicker.showingProperty().addListener((obs,b,b1)->{
            if(b1){
                datePicker.setValue(LocalDate.now());
                DatePickerContent content = (DatePickerContent)((DatePickerSkin)datePicker.getSkin()).getPopupContent();

                List<DateCell> cells = content.lookupAll(".day-cell").stream()
                        .filter(ce->!ce.getStyleClass().contains("next-month"))
                        .map(n->(DateCell)n)
                        .collect(Collectors.toList());

                // select initial range
                if(iniDate!=null && endDate!=null){
                    int ini=iniDate.getDayOfMonth();
                    int end=endDate.getDayOfMonth();
                    cells.stream()
                            .forEach(ce->ce.getStyleClass().remove("selected"));
                    cells.stream()
                            .filter(ce->Integer.parseInt(ce.getText())>=ini)
                            .filter(ce->Integer.parseInt(ce.getText())<=end)
                            .forEach(ce->ce.getStyleClass().add("selected"));
                }
                iniCell=null;
                endCell=null;
                content.setOnMouseDragged(e->{
                    Node n=e.getPickResult().getIntersectedNode();
                    DateCell c=null;
                    if(n instanceof DateCell){
                        c=(DateCell)n;
                    } else if(n instanceof Text){
                        c=(DateCell)(n.getParent());
                    }
                    if(c!=null && c.getStyleClass().contains("day-cell") &&
                            !c.getStyleClass().contains("next-month")){
                        if(iniCell==null){
                            iniCell=c;
                        }
                        endCell=c;
                    }
                    if(iniCell!=null && endCell!=null){
                        int ini=(int)Math.min(Integer.parseInt(iniCell.getText()),
                                Integer.parseInt(endCell.getText()));
                        int end=(int)Math.max(Integer.parseInt(iniCell.getText()),
                                Integer.parseInt(endCell.getText()));
                        cells.stream()
                                .forEach(ce->ce.getStyleClass().remove("selected"));
                        cells.stream()
                                .filter(ce->Integer.parseInt(ce.getText())>=ini)
                                .filter(ce->Integer.parseInt(ce.getText())<=end)
                                .forEach(ce->ce.getStyleClass().add("selected"));
                    }
                });
                content.setOnMouseReleased(e->{
                    if(iniCell!=null && endCell!=null){
                        iniDate=LocalDate.of(datePicker.getValue().getYear(),
                                datePicker.getValue().getMonth(),
                                Integer.parseInt(iniCell.getText()));
                        endDate=LocalDate.of(datePicker.getValue().getYear(),
                                datePicker.getValue().getMonth(),
                                Integer.parseInt(endCell.getText()));
                        System.out.println("Selection from "+iniDate+" to "+endDate);
                        if (!resultMap.containsKey(column)) {
                            resultMap.put(column, iniDate.toString()+" / " +endDate.toString());
                        } else {
                            resultMap.replace(column, iniDate.toString()+" / " +endDate.toString());
                        }

                        datePicker.setValue(iniDate);
                        int ini=iniDate.getDayOfMonth();
                        int end=endDate.getDayOfMonth();
                        cells.stream()
                                .forEach(ce->ce.getStyleClass().remove("selected"));
                        cells.stream()
                                .filter(ce->Integer.parseInt(ce.getText())>=ini)
                                .filter(ce->Integer.parseInt(ce.getText())<=end)
                                .forEach(ce->ce.getStyleClass().add("selected"));
                    }
                    endCell=null;
                    iniCell=null;
                });
            }
        });
    }

    private void setMap() {
        if (MainWindowController.getSelectionModelTypeName().equals("Flight")) {
            fieldMap = new HashMap<String, TextField>() {
                {
                    put("fromFlightToBoard.fromBoardToClient.passportID", passportField);
                    put("fromFlightToBoard.fromBoardToClient.lastName", lastNameField);
                    put("fromFlightToBoard.fromBoardToClient.firstName", firstNameField);
                    put("fromFlightToBoard.freeSeat", freeSeatField);
                    put("fromFlightToBoard.jetType", jetTypeField);
                    put("duration", timeField);
                    put("destination.city", destinationFieldCity);
                    put("fromFlightToBoard.tickets.price", priceField);
                    put("destination.airportCode", destinationFieldCode);
                    put("flights.departure.airportCode", departureFieldCode);
                    put("flights.departure.city", departureFieldCity);

                }
            };
            fieldMap.forEach((k, v) -> setAction(v, k));
            setDateAction(flightDateField, "date");
            setDateAction(purchaseField, "fromFlightToBoard.tickets.datePurchase");
            setDateAction(lastRepairField, "fromFlightToBoard.lastRepair");
        }
        if (MainWindowController.getSelectionModelTypeName().equals("Board")) {
            fieldMap = new HashMap<String, TextField>() {
                {
                    put("fromBoardToClient.passportID", passportField);
                    put("fromBoardToClient.lastName", lastNameField);
                    put("fromBoardToClient.firstName", firstNameField);
                    put("freeSeat", freeSeatField);
                    put("jetType", jetTypeField);
                    put("fromBoardToFlight.duration", timeField);
                    put("fromBoardToFlight.destination.city", destinationFieldCity);
                    put("tickets.price", priceField);
                    put("fromBoardToFlight.destination.airportCode", destinationFieldCode);
                    put("fromBoardToFlight.departure.airportCode", departureFieldCode);
                    put("fromBoardToFlight.departure.city", departureFieldCity);

                }
            };
            fieldMap.forEach((k, v) -> setAction(v, k));
            setDateAction(flightDateField, "fromBoardToFlight.date");
            setDateAction(purchaseField, "tickets.datePurchase");
            setDateAction(lastRepairField, "lastRepair");
        }
        if (MainWindowController.getSelectionModelTypeName().equals("Ticket")) {
            fieldMap = new HashMap<String, TextField>() {
                {
                    put("client.passportID", passportField);
                    put("client.lastName", lastNameField);
                    put("client.firstName", firstNameField);
                    put("board.freeSeat", freeSeatField);
                    put("board.jetType", jetTypeField);
                    put("board.fromBoardToFlight.duration", timeField);
                    put("board.fromBoardToFlight.destination.city", destinationFieldCity);
                    put("price", priceField);
                    put("board.fromBoardToFlight.destination.airportCode", destinationFieldCode);
                    put("board.fromBoardToFlight.departure.airportCode", departureFieldCode);
                    put("board.fromBoardToFlight.departure.city", departureFieldCity);
                }
            };
            fieldMap.forEach((k, v) -> setAction(v, k));
            setDateAction(flightDateField, "board.flights.date");
            setDateAction(purchaseField, "datePurchase");
            setDateAction(lastRepairField, "board.lastRepair");
        }
        if (MainWindowController.getSelectionModelTypeName().equals("Client")) {
            fieldMap = new HashMap<String, TextField>() {
                {
                    put("passportID", passportField);
                    put("lastName", lastNameField);
                    put("firstName", firstNameField);
                    put("fromClientToBoard.freeSeat", freeSeatField);
                    put("fromClientToBoard.jetType", jetTypeField);
                    put("fromClientToBoard.fromBoardToFlight.duration", timeField);
                    put("fromClientToBoard.fromBoardToFlight.destination.city", destinationFieldCity);
                    put("tickets.price", priceField);
                    put("fromClientToBoard.fromBoardToFlight.destination.airportCode", destinationFieldCode);
                    put("fromClientToBoard.fromBoardToFlight.departure.airportCode", departureFieldCode);
                    put("fromClientToBoard.fromBoardToFlight.departure.city", departureFieldCity);
                }
            };
            fieldMap.forEach((k, v) -> setAction(v, k));
            setDateAction(flightDateField, "fromClientToBoard.fromBoardToFlight.date");
            setDateAction(purchaseField, "tickets.datePurchase");
            setDateAction(lastRepairField, "fromClientToBoard.lastRepair");
        }
    }
}
