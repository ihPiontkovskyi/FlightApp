package gui.controllers.tableCellComponent;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;

public class ComboBoxCell<T,S> extends TableCell<T, S> {

    private ComboBox<S> comboBox;
    ObservableList list;

    public ComboBoxCell(ObservableList _list) {
        list =_list;
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (comboBox == null) {
            createComboBox();
        }
        setGraphic(this.comboBox);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(String.valueOf(getItem()));
        setGraphic(null);
    }
    @Override
    public void updateItem(S item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (comboBox != null) {
                    comboBox.setValue((S)getString());
                }
                setGraphic(comboBox);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }

    }

    private void createComboBox() {
        comboBox = new ComboBox<>();
        comboBox.setItems(list);
        if(getItem() != null) {
            comboBox.getSelectionModel().select(getItem());
        }
        else
        {
            comboBox.getSelectionModel().select(0);
        }
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        comboBox.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                commitEdit((S)comboBox.getSelectionModel().getSelectedItem());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
        comboBox.setOnAction(t->commitEdit((S)comboBox.getSelectionModel().getSelectedItem()));
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
