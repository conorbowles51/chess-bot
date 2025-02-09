package com.chess.engine.pieces;

import com.chess.engine.PieceColor;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_DIRECTIONS = { 8, 16, 7, 9 };

    public Pawn(final int tileIndex, final PieceColor color) {
        super(tileIndex, color);
    }

    @Override
    public Collection<Move> getLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int direction : CANDIDATE_MOVE_DIRECTIONS){
            final int candidateDestinationIdx = this.tileIndex + direction * this.color.getPawnDirection();

            if(!BoardUtils.isValidIndex(candidateDestinationIdx)){
                continue;
            }

            final Tile candidateDestinationTile = board.getTile(candidateDestinationIdx);
            final Piece candidateDestinationPiece = candidateDestinationTile.getPiece();

                // PASSIVE MOVES
            if(!candidateDestinationTile.isOccupied()) {
                if (direction == 8) {
                    // TODO: More to do here! Promotions!
                    legalMoves.add(new PassiveMove(board, this, candidateDestinationIdx));
                } else if (direction == 16 && this.isFirstMove()) {
                    final int jumpedOverTileIdx = candidateDestinationIdx + 8 * this.color.getPawnDirection();

                    if(!board.getTile(jumpedOverTileIdx).isOccupied()) {
                        legalMoves.add(new PassiveMove(board, this, candidateDestinationIdx));
                    }
                }
                // CAPTURES
            } else if(candidateDestinationPiece.color != this.color){
                if(direction == 7 && !((BoardUtils.EIGHTH_COLUMN[this.tileIndex] && this.color.isWhite()) ||
                                       (BoardUtils.FIRST_COLUMN[this.tileIndex] && this.color.isBlack())) ){
                    legalMoves.add(new CaptureMove(board, this, candidateDestinationIdx, candidateDestinationPiece));
                }
                else if(direction == 9 && !((BoardUtils.FIRST_COLUMN[this.tileIndex] && this.color.isWhite()) ||
                                              (BoardUtils.EIGHTH_COLUMN[this.tileIndex] && this.color.isBlack())) ){
                    legalMoves.add(new CaptureMove(board, this, candidateDestinationIdx, candidateDestinationPiece));
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
}
