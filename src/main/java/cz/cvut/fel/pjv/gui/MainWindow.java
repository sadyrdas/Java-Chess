package cz.cvut.fel.pjv.gui;
import cz.cvut.fel.pjv.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainWindow {
    private final Color lightTileColor = Color.decode("#FFFFFF");
    private final Color darkTileColor = Color.decode("#000000");
    private final BoardPanel boardpanel;
    private JFrame windowForGame;
    public MainWindow() {

        this.windowForGame = new JFrame("Dastan's chess game");
        this.windowForGame.setSize(800, 600);
        this.windowForGame.setLayout(new BorderLayout());
        this.windowForGame.setVisible(true);
        this.windowForGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.boardpanel = new BoardPanel();
        this.windowForGame.add(boardpanel, BorderLayout.CENTER);

    }
    // this class describes chess board for game
    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setSize(500,400);
            validate();
        }
    }
    //this class describes tile on the chess board
    private class TilePanel extends JPanel{
        private final int IdOfTile;

        TilePanel(final BoardPanel boardPanel, final int IdOfTile) {
            super(new GridBagLayout());
            this.IdOfTile = IdOfTile;
            setSize(15,15);
            makeTileColor();
            validate();
        }

        private void makeTileColor() {
            if(BoardUtils.FIRST_ROW[this.IdOfTile] || BoardUtils.THIRD_ROW[this.IdOfTile]
            || BoardUtils.FIFTH_ROW[this.IdOfTile] || BoardUtils.SEVENTH_ROW[this.IdOfTile]) {
                setBackground(this.IdOfTile % 2 == 0 ? lightTileColor : darkTileColor);
            }
            if(BoardUtils.SECOND_ROW[this.IdOfTile] ||BoardUtils.FOURTH_ROW[this.IdOfTile]
                || BoardUtils.SIXTH_ROW[this.IdOfTile] || BoardUtils.EIGHT_ROW[this.IdOfTile]) {
                setBackground(this.IdOfTile % 2 != 0 ? lightTileColor : darkTileColor);
            };
        }
    }
}



