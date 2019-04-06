package gui.controls.tableCellComponent;

import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class EditingCell<T, S> extends TableCell<T, S> {
    private TextField textField;

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        if(getItem() != null) {
            textField.setText(getString());
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
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
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                String value = textField.getText();
                if (value != null) {
                    commitEdit((S) value);
            } else {
                commitEdit(null);
            }
        } else if (t.getCode() == KeyCode.ESCAPE) {
            cancelEdit();
        }
    });
}

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
