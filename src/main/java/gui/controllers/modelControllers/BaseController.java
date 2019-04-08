package gui.controllers.modelControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Data;
import models.Airport;
import models.BaseModel;
import models.FlightInfo;
import service.Service;

import java.util.Map;
import java.util.Optional;

@Data
public abstract class BaseController {
    static ObservableList<BaseModel> changedList = FXCollections.observableArrayList();
    TableView tableView = new TableView();
    static Service service = null;
    static Map fieldValue = null;
    private boolean closingByUser = false;

    public abstract void add();

    public void saveOrUpdate() {
        service.saveOrUpdate(changedList);
        if (service.findAll(tableView.getId()).size() != tableView.getItems().size()) {
            Alert alert_ = new Alert(Alert.AlertType.ERROR, "An exception occurred!");
            alert_.setHeaderText("Error");
            alert_.showAndWait();
        } else {
            changedList.clear();
        }
    }

    public void delete() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?!");
        alert.setHeaderText("Warning");
        ButtonType buttonTypeDelete = new ButtonType("Delete");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeDelete, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeDelete) {
            if (!tableView.getSelectionModel().isEmpty()) {
                service.delete(tableView.getSelectionModel().getSelectedItem());
                ObservableList<BaseModel> forDelete = FXCollections.observableArrayList();
                changedList.forEach(e -> {
                    if (e.equals(tableView.getSelectionModel().getSelectedItem())
                    ) {
                        forDelete.add(e);
                    }
                });
                changedList.removeAll(forDelete);
                tableView.setItems(service.findAll(tableView.getId()));
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
            } else {
                Alert alert_ = new Alert(Alert.AlertType.ERROR, "An exception occurred!");
                alert_.setHeaderText("Error");
                alert_.showAndWait();
            }
        }

    }

    private boolean isEmpty() {
        return changedList.size() == 0;
    }

    private void confirmation() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to save the changes?");
        alert.setHeaderText("Attention");
        ButtonType buttonTypeSave = new ButtonType("Save and continue");
        ButtonType buttonTypeCancel = new ButtonType("Continue without saving");
        alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeSave) {
            saveOrUpdate();
        }
        changedList.clear();
    }

    void checkSet() {
        if (!isEmpty()) {
            confirmation();
        }
    }

    public boolean startSearch() {
        if (!isEmpty()) {
            confirmation();
        }
        if (getEntity() != Airport.class && getEntity() != FlightInfo.class) {
            try {
                Stage stage = new Stage();
                FXMLLoader root = new FXMLLoader(getClass().getResource("/gui/searchWindow.fxml"));
                AnchorPane anchorPane = root.load();
                Scene scene = new Scene(anchorPane, 305, 400);
                stage.setTitle("Search");
                stage.setScene(scene);
                stage.getIcons().add(new Image("icons/search.png"));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(tableView.getScene().getWindow());
                stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
                stage.showAndWait();
                return !closingByUser;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Search for this table is unacceptable");
            alert.setHeaderText("Attention");
            alert.showAndWait();
        }
        return closingByUser;
    }

    private <T extends Event> void closeWindowEvent(T t) {
        closingByUser = true;
    }

    public static void setMap(Map map) {
        fieldValue = map;
    }

    public ObservableList search() {
        return service.search(fieldValue);
    }

    public void refresh() {
        tableView.getItems().clear();
        tableView.setItems(service.findAll(tableView.getId()));
        changedList.clear();
    }
}
