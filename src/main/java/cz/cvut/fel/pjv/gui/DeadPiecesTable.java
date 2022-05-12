package cz.cvut.fel.pjv.gui;

import com.google.common.primitives.Ints;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.piece.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static cz.cvut.fel.pjv.gui.MainWindow.*;

public class DeadPiecesTable extends JPanel {

    private final JPanel northTable;
    private final JPanel southTable;

    private static final Color TABLE_COLOR = Color.decode("0xFDF5E6");
    private static final EtchedBorder TABLE_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Dimension DEAD_PIECES = new Dimension(40,80);
    public DeadPiecesTable() {
        super(new BorderLayout());
        setBackground(Color.decode("0xFDF5E6"));
        setBorder(TABLE_BORDER);
        this.northTable = new JPanel(new GridLayout(8, 2));
        this.southTable = new JPanel(new GridLayout(8, 2));
        this.northTable.setBackground(TABLE_COLOR);
        this.southTable.setBackground(TABLE_COLOR);
        this.add(this.northTable, BorderLayout.NORTH);
        this.add(this.southTable, BorderLayout.SOUTH);
        setPreferredSize(DEAD_PIECES);
    }

    //this method describes new lists, which will have all dead pieces by team
    public void redo(final MoveLog moveLog){
        southTable.removeAll();
        northTable.removeAll();

        final List<Piece> whiteDeadPieces = new ArrayList<>();
        final List<Piece> blackDeadPieces = new ArrayList<>();

        for(final Move move : moveLog.getMoves()) {
            if (move.isAttack()) {
                final Piece deadPiece = move.getAttackedPiece();
                if(deadPiece.getPieceTeam().isWhite()) {
                    whiteDeadPieces.add(deadPiece);
                } else if (deadPiece.getPieceTeam().isBlack()) {
                    blackDeadPieces.add(deadPiece);
                }else {
                    throw new RuntimeException("Mistake!");
                }
            }
        }
        Collections.sort(whiteDeadPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });
        Collections.sort(blackDeadPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        for (final Piece deadPiece : whiteDeadPieces) {
            try {
                String filename = deadPiece.getPieceTeam().toString().substring(0, 1) + "" + deadPiece.toString()
                        + ".gif";
                final BufferedImage image = ImageIO.read(getClass().getResource("/deadPieces/" + filename));
                final ImageIcon ic = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.northTable.add(imageLabel);
            }catch (final IOException e) {
                e.printStackTrace();

            }
        }
        for (final Piece deadPiece : blackDeadPieces) {
            try {

                String filename = deadPiece.getPieceTeam().toString().substring(0, 1) + "" + deadPiece.toString()
                        + ".gif";
                final BufferedImage image = ImageIO.read(getClass().getResource("/deadPieces/" + filename));
                final ImageIcon ic = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.southTable.add(imageLabel);
            }catch (final IOException e) {
                e.printStackTrace();

            }
        }
        validate();
    }

}
