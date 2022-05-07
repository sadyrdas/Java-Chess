package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.gui.MainWindow;

public class StandardBoard {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();

        System.out.println(board);

        MainWindow mw = new MainWindow();
    }
}
