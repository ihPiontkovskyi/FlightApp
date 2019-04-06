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

    private Map<String, String> resultMap = new HashMap<String, String>();
    private Map<String, TextField> fieldMap = new HashMap<String, TextField>() {
        {
            put("classType", classField);
            put("passportID", passportField);
            put("lastName", lastNameField);
            put("firstName", firstNameField);
            put("freeSeat", freeSeatField);
            put("jetType", jetTypeField);
            put("duration", timeField);
            put("city", cityField);
            put("price", priceField);
            put("airportCode", codeField);

        }
    };


    @FXML
    public void initialize() {
        fieldMap.forEach((k, v) -> setAction(v, k));
        setDateAction(flightDateField, "date");
        setDateAction(purchaseField, "datePurchase");
        setDateAction(lastRepairField, "lastRepair");
        startSearch.setOnAction(event -> {
            BaseController.setMap(resultMap);
            ((Node)event.getSource()).getScene().getWindow().hide();
        });
    }

    private void setAction(TextField field, String column) {
        field.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                if (t.getText().trim() != "") {
                    resultMap.put(column, t.getText());
                } else resultMap.remove(column);
            }
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
}
