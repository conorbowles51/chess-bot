package com.chess.engine.player;

import com.chess.engine.PieceColor;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveStatus;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

public class Player {

    private final Board board;
    private final Collection<Move> legalMoves;
    private final Collection<Move> opponentLegalMoves;
    private final PieceColor pieceColor;

    public Player(Board board, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves, PieceColor pieceColor) {
        this.board = board;
        this.legalMoves = pieceColor.isWhite() ? whiteLegalMoves : blackLegalMoves;
        this.opponentLegalMoves = pieceColor.isWhite() ? blackLegalMoves : whiteLegalMoves;
        this.pieceColor = pieceColor;
    }

    public PieceColor getPieceColor(){
        return this.pieceColor;
    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    public Player getOpponent(){
        return pieceColor.isWhite() ?
                board.getBlackPlayer(): board.getWhitePlayer();
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    public boolean isChecked(){
        return board.calculateAttacksOnTile(getKing().getTileIndex(), opponentLegalMoves).isEmpty();
    }

    public boolean isCheckmated(){
        return isChecked() && !hasEscapeMoves();
    }

    public boolean isStalemated(){
        return !isChecked() && !hasEscapeMoves();
    }

    private boolean hasEscapeMoves(){
        for(final Move move : legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isMoveSuccess()){
                return true;
            }
        }
        return false;
    }

    public boolean hasCastled(){
        return false;
    }

    public MoveTransition makeMove(Move move){
        if(!isMoveLegal(move)){
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transistionBoard = move.execute();

        final Collection<Move> kingAttacks = transistionBoard.calculateAttacksOnTile(transistionBoard.getCurrentPlayer().getOpponent().getKing().getTileIndex(),
                                                                                     transistionBoard.getCurrentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        return new MoveTransition(transistionBoard, move, MoveStatus.COMPLETED);
    }

    public Collection<Piece> getActivePieces(){
        return board.getActivePieces(pieceColor);
    }

    private King getKing(){
        for(final Piece piece : board.getActivePieces(pieceColor)){
            if(piece.isKing()){
                return (King) piece;
            }
        }

        throw new RuntimeException("Invalid board state: No King found on board for " + (pieceColor.isWhite() ? "White" : " Black") + " Player");
    }
}
