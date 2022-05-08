package cz.cvut.fel.pjv.gui;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainWindow {
    private final Color lightTileColor = Color.decode("#FFFFFF");
    private final Color darkTileColor = Color.decode("#560319");
    private Board chessBoard;
    private static String defaultPathForImagesOFPieces = "C:\\Users\\Acer\\IdeaProjects\\CHESS\\src\\main\\resources\\chessPieces\\";

    private final BoardPanel boardpanel;
    private JFrame windowForGame;
    public MainWindow() {

        this.windowForGame = new JFrame("Dastan's chess game");
        this.windowForGame.setSize(600, 600);
        this.windowForGame.setLayout(new BorderLayout());
        this.chessBoard = Board.createStandardBoard();
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
            setSize(500,450);
            validate();
        }
    }
    //this class describes tile on the chess board
    private class TilePanel extends JPanel{
        private final int IdOfTile;

        TilePanel(final BoardPanel boardPanel, final int IdOfTile) {
            super(new GridBagLayout());
            this.IdOfTile = IdOfTile;
            setSize(10,10);
            makeTileColor();
            validate();
            makeTileImages(chessBoard);
        }
        private void makeTileImages(Board board) {
            this.removeAll();
            if(board.getTile(this.IdOfTile).IsTileOccupied()) {
                try {
                    final BufferedImage image =
                            ImageIO.read(new File(defaultPathForImagesOFPieces + board.getTile(this.IdOfTile).getPiece().getPieceTeam().toString().substring(0, 1) +
                                    board.getTile(this.IdOfTile).getPiece().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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



