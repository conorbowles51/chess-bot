package com.chess.engine.board;

import com.chess.engine.PieceColor;
import com.chess.engine.pieces.*;
import com.chess.engine.player.Player;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class Board {
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final Player whitePlayer;
    private final Player blackPlayer;
    private final PieceColor nextToMove;

    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = getActivePieces(PieceColor.WHITE);
        this.blackPieces = getActivePieces(PieceColor.BLACK);

        final Collection<Move> whiteLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new Player(this, whiteLegalMoves, blackLegalMoves, PieceColor.WHITE);
        this.blackPlayer = new Player(this, whiteLegalMoves, blackLegalMoves, PieceColor.BLACK);
        this.nextToMove = builder.nextToMove;
    }


    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final Piece piece : pieces){
            legalMoves.addAll(piece.getLegalMoves(this));
        }

        return ImmutableList.copyOf(legalMoves);
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Player getCurrentPlayer() {
        return this.nextToMove == PieceColor.WHITE ? this.whitePlayer : this.blackPlayer;
    }

    public Collection<Piece> getActivePieces(PieceColor pieceColor) {
        final List<Piece> pieces = new ArrayList<>();

        for(final Tile tile : this.gameBoard){
            final Piece piece = tile.getPiece();

            if(piece != null && piece.getColor() == pieceColor){
                pieces.add(piece);
            }
        }

        return ImmutableList.copyOf(pieces);
    }

    @Override
    public String toString(){
        final StringBuilder boardString = new StringBuilder();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            boardString.append(String.format("%3s", this.gameBoard.get(i).toString()));

            if((i + 1) % 8 == 0 ){
                boardString.append("\n");
            }
        }

        return boardString.toString();
    }

    private static List<Tile> createGameBoard(final Builder builder){
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }

        return ImmutableList.copyOf(tiles);
    }

    public Tile getTile(final int index){
        return gameBoard.get(index);
    }

    public static Board createStandardBoard(){
        final Builder builder = new Builder();

        // Black pieces
        builder.setPiece(new Rook(0, PieceColor.BLACK));
        builder.setPiece(new Knight(1, PieceColor.BLACK));
        builder.setPiece(new Bishop(2, PieceColor.BLACK));
        builder.setPiece(new Queen(3, PieceColor.BLACK));
        builder.setPiece(new King(4, PieceColor.BLACK));
        builder.setPiece(new Bishop(5, PieceColor.BLACK));
        builder.setPiece(new Knight(6, PieceColor.BLACK));
        builder.setPiece(new Rook(7, PieceColor.BLACK));
        // White pieces
        builder.setPiece(new Rook(56, PieceColor.WHITE));
        builder.setPiece(new Knight(57, PieceColor.WHITE));
        builder.setPiece(new Bishop(58, PieceColor.WHITE));
        builder.setPiece(new Queen(59, PieceColor.WHITE));
        builder.setPiece(new King(60, PieceColor.WHITE));
        builder.setPiece(new Bishop(61, PieceColor.WHITE));
        builder.setPiece(new Knight(62, PieceColor.WHITE));
        builder.setPiece(new Rook(63, PieceColor.WHITE));
        // Pawns
        for (int i = 0; i < 8; i++) {
            builder.setPiece(new Pawn(48 + i, PieceColor.WHITE));
            builder.setPiece(new Pawn(8 + i, PieceColor.BLACK));
        }

        builder.setNextToMove(PieceColor.WHITE);

        return builder.build();
    }

    public Collection<Move> calculateAttacksOnTile(int tileIndex, Collection<Move> opponentLegalMoves) {
        final List<Move> attacks = new ArrayList<>();

        for(Move move : opponentLegalMoves){
            if(tileIndex == move.getDestinationIndex()){
                attacks.add(move);
            }
        }

        return ImmutableList.copyOf(attacks);
    }

    public static class Builder {
        Map<Integer, Piece> boardConfig;
        PieceColor nextToMove;

        public Builder(){
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getTileIndex(), piece);
            return this;
        }

        public Builder setNextToMove(final PieceColor nextToMove){
            this.nextToMove = nextToMove;
            return this;
        }

        public Board build(){
            return new Board(this);
        }
    }
}
