package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece piece;
    final int destinationIdx;

    private Move(Board board, Piece piece, int destinationIdx) {
        this.board = board;
        this.piece = piece;
        this.destinationIdx = destinationIdx;
    }

    public static final class PassiveMove extends Move {

        public PassiveMove(final Board board, final Piece piece, final int destinationIdx) {
            super(board, piece, destinationIdx);
        }
    }

    public static final class CaptureMove extends Move {

        final Piece capturedPiece;

        public CaptureMove(final Board board, final Piece piece, final int destinationIdx, final Piece capturedPiece) {
            super(board, piece, destinationIdx);
            this.capturedPiece = capturedPiece;
        }
    }
}
