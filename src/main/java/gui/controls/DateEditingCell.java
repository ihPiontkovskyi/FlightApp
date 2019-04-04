package gui.controls;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;

public class DateEditingCell<S> extends TableCell<S, Date> {

    private DatePicker datePicker;

    DateEditingCell() {

        super();


        if (datePicker == null) {
            createDatePicker();
        }
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        Platform.runLater(() -> datePicker.requestFocus());

    }

    @Override
    public void updateItem(Date item, boolean empty) {

        super.updateItem(item, empty);

        SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");

        if (null == this.datePicker) {
            System.out.println("datePicker is NULL");
        }

        if (empty) {
            setText(null);
            setGraphic(null);
            System.out.println("datePicker is EMPTY");
        } else {

            if (isEditing()) {
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            } else {
                setDatePikerDate(smp.format(item));
                setText(smp.format(item));
                setGraphic(this.datePicker);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }

    private void setDatePikerDate(String dateAsStr) {

        LocalDate ld;
        int jour, mois, annee;

        jour = mois = annee = 0;
        try {
            jour = Integer.parseInt(dateAsStr.substring(0, 2));
            mois = Integer.parseInt(dateAsStr.substring(3, 5));
            annee = Integer.parseInt(dateAsStr.substring(6));
        } catch (NumberFormatException e) {
            System.out.println("setDatePikerDate / unexpected error " + e);
        }

        ld = LocalDate.of(annee, mois, jour);
        datePicker.setValue(ld);
    }

    private void createDatePicker() {
        this.datePicker = new DatePicker();
        datePicker.setEditable(true);

        datePicker.setOnAction(t -> {
            LocalDate date = datePicker.getValue();

            SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
            cal.set(Calendar.MONTH, date.getMonthValue() - 1);
            cal.set(Calendar.YEAR, date.getYear());

            setText(smp.format(cal.getTime()));
            Date date_ = Date.valueOf(cal.getTime().toInstant().atZone(ZoneOffset.UTC).toLocalDate());
            commitEdit(date_);
        });

        datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                commitEdit(Date.valueOf((datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().atZone(ZoneOffset.UTC).toLocalDate())));
            }
        });

        setAlignment(Pos.CENTER);
    }

    @Override
    public void startEdit() {
        super.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

}
