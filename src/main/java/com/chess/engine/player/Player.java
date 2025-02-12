package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

public class Player {

    private final Board board;
    private final King king;
    private final Collection<Move> legalMoves;

    public Player(Board board, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves, boolean isWhite) {
        this.board = board;
        this.king = getKing();
        this.legalMoves = isWhite ? whiteLegalMoves : blackLegalMoves;
    }

    private King getKing(){
        for(final Piece piece : this.board.getActivePieces()){
            if(piece.isKing()){
                return (King) piece;
            }
        }
    }
}
