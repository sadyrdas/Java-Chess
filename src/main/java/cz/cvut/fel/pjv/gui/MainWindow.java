package cz.cvut.fel.pjv.gui;
import com.google.common.collect.Lists;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.board.Tile;
import cz.cvut.fel.pjv.piece.Piece;
import cz.cvut.fel.pjv.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;


public class MainWindow {
    private final Color lightTileColor = Color.decode("#FFFFFF");
    private final Color darkTileColor = Color.decode("#560319");
    private Board chessBoard;

    private Tile originTile;
    private Tile DestinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private boolean hightlightLegalMoves;

    private final BoardPanel boardpanel;
    private JFrame windowForGame;
    public MainWindow() {

        this.windowForGame = new JFrame("Dastan's chess game");
        this.windowForGame.setLayout(new BorderLayout());
        final JMenuBar MenuBar = createMenuBar();
        this.windowForGame.setJMenuBar(MenuBar);
        this.windowForGame.setSize(600, 600);
        this.chessBoard = Board.createStandardBoard();
        this.boardpanel = new BoardPanel();
        this.boardDirection = BoardDirection.NORMAL;
        this.hightlightLegalMoves = false;
        this.windowForGame.add(boardpanel, BorderLayout.CENTER);
        this.windowForGame.setVisible(true);

    }
    private JMenuBar createMenuBar() {
        final JMenuBar MenuBar = new JMenuBar();
        MenuBar.add(createFileMenuBar());
        MenuBar.add(createPreferencesMenu());
        return MenuBar;
    }
    private JMenu createFileMenuBar() {
        final JMenu file = new JMenu("File");
        final JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(exit);
        return file;
    }
    private JMenu createPreferencesMenu() {

        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoard = new JMenuItem("Flip Board");
        flipBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardpanel.makeBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoard);
        preferencesMenu.addSeparator();


        final JCheckBoxMenuItem cbLegalMoveHighlighter = new JCheckBoxMenuItem(
                "Highlight Legal Moves", false);

        cbLegalMoveHighlighter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hightlightLegalMoves = cbLegalMoveHighlighter.isSelected();

            }
        });

        preferencesMenu.add(cbLegalMoveHighlighter);
        preferencesMenu.add(cbLegalMoveHighlighter);
        return preferencesMenu;
    }
    //this method make like flip black and white team. Reverse from guava
    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();

    }




    // this class describes chess board for game
    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setSize(500, 450);
            validate();
        }
        //if is preference normal than will for loop in normal way, if preference flipper than will in opposite way
        public void makeBoard(final Board board) {
            removeAll();
            for (final TilePanel tilePanel: boardDirection.traverse(boardTiles)) {
                tilePanel.makeTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    public static class MoveLog {
        private final List<Move> moves;
        MoveLog() {
            this.moves = new ArrayList<>();
        }
        public List<Move> getMoves(){
            return this.moves;
        }
        public void addMove(final Move move) {
            this.moves.add(move);
        }
        public int size() {
            return this.moves.size();
        }
        public void clear() {
            this.moves.clear();
        }
        public Move removeMove(int index) {
            return this.moves.remove(index);
        }
        public boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }
    }



    //this class describes tile on the chess board
    private class TilePanel extends JPanel{
        private final int IdOfTile;

        TilePanel(final BoardPanel boardPanel, final int IdOfTile) {
            super(new GridBagLayout());
            this.IdOfTile = IdOfTile;
            setSize(10,10);
            validate();
            makeTileColor();
            makeTileImages(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //for example, when you have mistake click - it will cancel move.
                    if(isRightMouseButton(e)) {
                        originTile = null;
                        DestinationTile = null;
                        humanMovedPiece = null;
                    }else if (isLeftMouseButton(e)) {
                        if (originTile == null) {
                            originTile = chessBoard.getTile(IdOfTile);
                            humanMovedPiece = originTile.getPiece();
                            if(humanMovedPiece == null) {
                                originTile = null;
                            }
                        } else {
                            DestinationTile = chessBoard.getTile(IdOfTile);
                            final Move move = Move.MoveFactory.createMove(chessBoard, originTile.getTileCoordinate(), DestinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDID()) {
                                chessBoard = transition.getTransitionBoard();
                            }
                            //That will not be pastmove+nextmove;
                            originTile = null;
                            DestinationTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.makeBoard(chessBoard);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            validate();
        }
        public void makeTile(final Board board) {
            makeTileColor();
            makeTileImages(board);
            highlightLegals(board);
            validate();
            repaint();

        }
        private void makeTileImages(final Board board) {
            this.removeAll();
            if(board.getTile(this.IdOfTile).IsTileOccupied()) {
                try {
                    String fileName = board.getTile(this.IdOfTile).getPiece().getPieceTeam().toString().substring(0, 1) +
                            board.getTile(this.IdOfTile).getPiece().toString().toUpperCase() + ".gif";
                    final BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/chessPieces/" + fileName));
                    add(new JLabel(new ImageIcon(image)));


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        private void highlightLegals(final Board board) {
            if (hightlightLegalMoves) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestination() == this.IdOfTile) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/legalmove.png")))));
                        }
                        catch (final IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if(humanMovedPiece != null && humanMovedPiece.getPieceTeam() == board.currentPlayer().getTeam()) {
                return humanMovedPiece.writeLegalMoves(board);
            }
            return Collections.emptyList();
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


