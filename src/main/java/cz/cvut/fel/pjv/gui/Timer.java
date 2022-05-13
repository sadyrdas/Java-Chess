package cz.cvut.fel.pjv.gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer extends JPanel {
    private final JLabel blackTimer;
    private final JLabel whiteTimer;
    int time1=0;
    int time2=0;
    public Timer(int time) {
        super(new BorderLayout());
        time1=time2=time;
        setBackground(Color.decode("0xFDF5E6"));
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
        this.blackTimer = new JLabel();
        this.whiteTimer = new JLabel();
        this.add(this.blackTimer, BorderLayout.NORTH);
        this.add(this.whiteTimer, BorderLayout.SOUTH);

        this.whiteTimer.setSize(100, 200);
        this.blackTimer.setSize(100, 200);
        sync();

    }
    void sync(){
        Date date = new Date((long)(time1*1000));
        this.whiteTimer.setText(new SimpleDateFormat("mm:ss").format(date));
        date = new Date((long)(time2*1000));
        this.blackTimer.setText(new SimpleDateFormat("mm:ss").format(date));
    }
    Boolean dec(Boolean player){
        if(player)
            time1--;
        else
            time2--;
        sync();
        return (time1!=0 && time2!=0);
    }
}
