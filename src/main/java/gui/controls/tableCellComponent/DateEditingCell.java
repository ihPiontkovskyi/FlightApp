package gui.controls.tableCellComponent;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class DateEditingCell<S, T> extends TableCell<S, T> {

    private DatePicker datePicker;

    public DateEditingCell() {

        super();

        if (datePicker == null) {
            createDatePicker();
        }
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Platform.runLater(() -> datePicker.requestFocus());
    }

    @Override
    public void updateItem(T item, boolean empty) {

        super.updateItem(item, empty);

        SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");

        if (null == this.datePicker) {
            System.out.println("datePicker is NULL");
        }

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            if (isEditing()) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            } else {
                setDatePikerDate(smp.format(item));
                setText(smp.format(item));
                setGraphic(this.datePicker);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }

    private void setDatePikerDate(String dateAsStr) {
        if(dateAsStr != null || !dateAsStr.isEmpty()) {
            LocalDate ld;
            int day, month, year;

            day = month = year = 0;
            try {
                day = Integer.parseInt(dateAsStr.substring(0, 2));
                month = Integer.parseInt(dateAsStr.substring(3, 5));
                year = Integer.parseInt(dateAsStr.substring(6));
            } catch (NumberFormatException e) {
                System.out.println("setDatePikerDate / unexpected error " + e);
            }

            ld = LocalDate.of(year, month, day);
            datePicker.setValue(ld);
        }
        else
        {
            datePicker.setValue(LocalDate.now());
        }
    }

    private void createDatePicker() {
        this.datePicker = new DatePicker();
        datePicker.setPromptText("dd/MM/yyyy");
        datePicker.setEditable(true);
        datePicker.setOnAction(t -> {
            LocalDate date = datePicker.getValue();

            SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
            cal.set(Calendar.MONTH, date.getMonthValue() - 1);
            cal.set(Calendar.YEAR, date.getYear());

            setText(smp.format(cal.getTime()));
            commitEdit((T) cal.getTime());
        });
    }

    @Override
    public void startEdit() {
        super.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    public void commitEdit(T item) {
        if (!isEditing() && !item.equals(getItem())) {
            TableView<S> table = getTableView();
            if (table != null) {
                TableColumn<S, T> column = getTableColumn();
                CellEditEvent<S, T> event = new CellEditEvent<>(table,
                        new TablePosition<S, T>(table, getIndex(), column),
                        TableColumn.editCommitEvent(), item);
                Event.fireEvent(column, event);
            }
        }


        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

}