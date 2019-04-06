package gui.controllers.tableCellComponent;


import javafx.application.Platform;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSpinnerCell<S,T> extends TableCell<S, T> {

    private Spinner<LocalTime>  spinner = null;

    @Override
    public void startEdit() {
        super.startEdit();
        if(spinner == null)
        {
            createTimeSpinner();
        }
        spinner.getValueFactory().setValue(getTime());
        if (!isEmpty()) {
            super.startEdit();
            setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    Platform.runLater(() -> commitEdit((T)spinner.getValue()));
                }
            });

            setText(null);
            setGraphic(spinner);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getString());
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (spinner != null) {
                    spinner.getValueFactory().setValue(getTime());
                }
                setText(null);
                setGraphic(spinner);
            } else {
                if(getItem()!= null) {
                    setText(getString());
                }
                setGraphic(null);
            }
        }
    }
    private void createTimeSpinner()
    {
        spinner = new Spinner(new SpinnerValueFactory() {

            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), DateTimeFormatter.ofPattern("HH:mm")));
            }

            @Override
            public void decrement(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(steps));
                }
            }

            @Override
            public void increment(int steps) {
                if (this.getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusMinutes(steps));
                }
            }
        });
        spinner.setEditable(true);
        spinner.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                LocalTime value = spinner.getValue();
                if (value != null) {
                    commitEdit((T) value);
                } else {
                    commitEdit(null);
                }
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }
    private LocalTime getTime() {
        if(getItem() == null)
        {
            return LocalTime.parse("01:00");
        }
        else
        {
            return LocalTime.parse(getString());
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}