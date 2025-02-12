package com.chess.engine.pieces;

import com.chess.engine.PieceColor;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final int tileIndex;
    protected final PieceColor color;
    protected final boolean hasMoved;

    public Piece(final int tileIndex, final PieceColor color){
        this.tileIndex = tileIndex;
        this.color = color;
        this.hasMoved = false;
    }

    public boolean isKing(){
        return false;
    }

    public int getTileIndex(){
        return  this.tileIndex;
    }

    public PieceColor getColor(){
        return this.color;
    }
    public abstract Collection<Move> getLegalMoves(final Board board);

    public boolean isFirstMove(){
        return !this.hasMoved;
    }
}
