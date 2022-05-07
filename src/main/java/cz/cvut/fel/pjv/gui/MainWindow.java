package cz.cvut.fel.pjv.gui;
import cz.cvut.fel.pjv.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainWindow {
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
        }
    }
}



