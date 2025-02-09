package com.chess.engine;

public enum PieceColor {
    WHITE {
        @Override
        public int getPawnDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    },
    BLACK {
        @Override
        public int getPawnDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    };

    public abstract int getPawnDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
}
