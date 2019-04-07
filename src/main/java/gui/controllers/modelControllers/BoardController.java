package gui.controllers.modelControllers;

import gui.controllers.tableCellComponent.DateEditingCell;
import gui.controllers.tableCellComponent.EditingCell;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Board;
import service.ServiceImpl;

import java.sql.Date;
import java.time.ZoneId;

public class BoardController extends BaseController {
    private static boolean setColumn = false;

    public BoardController(TableView table) {
        tableView.getItems().clear();
        tableView = table;
        checkSet();
        service = new ServiceImpl<Board>();
        setDynamicColumn();
        table.setItems(service.findAll(table.getId()));
    }

    @Override
    public void add() {
        Board board = new Board();
        tableView.getItems().add(board);
        changedSet.add(board);
    }

    @Override
    public Class getEntity() {
        return Board.class;
    }

    private void setDynamicColumn() {
        tableView.getColumns().clear();
        TableColumn boardLastRepairColumn = new TableColumn("Last repair");
        boardLastRepairColumn.setMinWidth(100);
        boardLastRepairColumn.setCellValueFactory(new PropertyValueFactory<Board, Date>("lastRepair"));
        boardLastRepairColumn.setCellFactory(p -> new DateEditingCell<Board, java.util.Date>());
        boardLastRepairColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, java.util.Date>>) t -> {
            t.getRowValue().setLastRepair(Date.valueOf(t.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            changedSet.add(t.getRowValue());
        });
        TableColumn boardFreeSeatColumn = new TableColumn("Free seat");
        boardFreeSeatColumn.setMinWidth(100);
        boardFreeSeatColumn.setCellValueFactory(new PropertyValueFactory<Board, String>("freeSeat"));
        boardFreeSeatColumn.setCellFactory(p -> new EditingCell<Board, String>());
        boardFreeSeatColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, String>>) t -> {
            try {
                Integer value = Integer.parseInt(t.getNewValue());
                if (value < 0) {
                    Alert alert_ = new Alert(Alert.AlertType.ERROR, "An exception occurred!");
                    alert_.setHeaderText("Error");
                    alert_.showAndWait();
                    t.getRowValue().setFreeSeat(0);
                    tableView.refresh();
                } else {
                    t.getRowValue().setFreeSeat(Integer.parseInt(t.getNewValue()));
                    changedSet.add(t.getRowValue());
                }
            } catch (Exception ex) {
                Alert alert_ = new Alert(Alert.AlertType.ERROR, "An exception occurred!");
                alert_.setHeaderText("Error");
                alert_.showAndWait();
                t.getRowValue().setFreeSeat(0);
                tableView.refresh();
            }
        });
        TableColumn boardJetTypeColumn = new TableColumn("Jet type");
        boardJetTypeColumn.setMinWidth(100);
        boardJetTypeColumn.setCellValueFactory(new PropertyValueFactory<Board, String>("jetType"));
        boardJetTypeColumn.setCellFactory(p -> new EditingCell<Board, String>());
        boardJetTypeColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Board, String>>) t -> {
            t.getRowValue().setJetType(t.getNewValue());
            changedSet.add(t.getRowValue());
        });
        tableView.getColumns().addAll(boardFreeSeatColumn, boardJetTypeColumn, boardLastRepairColumn);
    }
}
