package cz.cvut.fel.pjv.gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
//just jpanel for timer for black and white teams
public class Timer extends JPanel {
    private final JLabel blackTimer;
    private final JLabel whiteTimer;

    public Timer() {
        super(new BorderLayout());
        setBackground(Color.decode("0xFDF5E6"));
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
        this.blackTimer = new JLabel();
        this.whiteTimer = new JLabel();
        this.add(this.blackTimer, BorderLayout.NORTH);
        this.add(this.whiteTimer, BorderLayout.SOUTH);
        this.whiteTimer.setSize(100, 200);
        this.whiteTimer.setText("0:00:00");
        this.blackTimer.setSize(100, 200);
        this.blackTimer.setText("0:00:00");

    }
}
