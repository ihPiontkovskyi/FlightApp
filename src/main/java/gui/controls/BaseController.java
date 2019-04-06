package gui.controls;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import models.BaseModel;
import service.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class BaseController {
    static Set<BaseModel> changedSet = new HashSet<BaseModel>();
    TableView tableView = new TableView();
    Service service = null;

    abstract void add();

    void saveOrUpdate() {
        service.saveOrUpdate(changedSet);
        if (service.findAll(tableView.getId()).size() != tableView.getItems().size()) {
            Alert alert_ = new Alert(Alert.AlertType.ERROR, "An exception occurred!");
            alert_.setHeaderText("Error");
            alert_.showAndWait();
        } else {
            changedSet.clear();
        }
    }

    void delete() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?!");
        alert.setHeaderText("Warning");
        ButtonType buttonTypeDelete = new ButtonType("Delete");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeDelete, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeDelete) {
            if (!tableView.getSelectionModel().isEmpty()) {
                service.delete(tableView.getSelectionModel().getSelectedItem());
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
        return changedSet.size() == 0;
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
        changedSet.clear();
    }

    void checkSet() {
        if (!isEmpty()) {
            confirmation();
        }
    }
}
