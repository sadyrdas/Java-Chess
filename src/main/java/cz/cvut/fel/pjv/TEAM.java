package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.player.BlackPlayer;
import cz.cvut.fel.pjv.player.Player;
import cz.cvut.fel.pjv.player.WhitePlayer;

//In this class, I describe the privatization of figures to team.
public enum TEAM {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player pickplayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player pickplayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract Player pickplayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
