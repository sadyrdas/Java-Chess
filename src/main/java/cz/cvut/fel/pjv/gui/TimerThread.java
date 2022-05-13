package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.gui.MainWindow.*;

public class TimerThread extends Thread {
    MainWindow window;
    TimerThread(MainWindow w){
        window=w;
    }
    @Override
    public void run() {

        while(window.myTimer.dec(window.chessBoard.currentPlayer().getTeam()== TEAM.WHITE)){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        window.chessBoard.currentPlayer().timeout();
    }
}
