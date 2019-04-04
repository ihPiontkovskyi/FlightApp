package service;

import dao.DAO;
import dao.daoImpl.BoardDAOImpl;
import javafx.collections.ObservableList;
import models.Board;

public class BoardService implements AbstractService<Board> {
    private DAO<Board> boardDAO = new BoardDAOImpl();

    public BoardService() {
    }

    public ObservableList<Board> FindAll() {
        return boardDAO.FindAll();
    }
    public void Delete(Board board){ boardDAO.DeleteItem(board);}
    public void Update(ObservableList<Board> boards) { }

}
