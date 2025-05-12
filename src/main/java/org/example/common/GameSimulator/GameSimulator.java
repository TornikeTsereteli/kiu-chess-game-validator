package org.example.common.GameSimulator;

import lombok.Getter;
import org.example.common.checkmateDetector.CheckmateDetector;
import org.example.common.checkmateDetector.StandardCheckmateDetector;
import org.example.model.*;
import org.example.model.enums.ChessPiece;
import org.example.model.enums.PieceColor;

import java.util.List;

public class GameSimulator implements GameSimulatorBase{

    @Getter
    private Board board;

    public GameSimulator(Board board) {
        this.board = board;
    }


    @Override
    public void SimulateGame(List<ChessMove> moves) throws Exception {

//        CheckmateDetector checkmateDetector = new StandardCheckmateDetector(board);
//
//        for(ChessMove move: moves){
//            PieceColor color = board.getTurn() ? PieceColor.BLACK : PieceColor.WHITE;
//            if (checkmateDetector.isInCheck(color) || checkmateDetector.isStalemate(color)){
//                throw new Exception("checkmate/stalemate no move can be made");
//            }
//
//            Square destination = getSquare(board, move);
//            Piece piece = getPiece(board, move,destination);
//            piece.move(destination,board);
//
//            PieceColor checkColor = board.getTurn()? PieceColor.WHITE : PieceColor.BLACK;
//            if(checkmateDetector.isInCheck(checkColor)){
//                throw new Exception("player moved illegal move there is Check");
//            }
//            board.toggleTurn();
//        }
        for(ChessMove move: moves){
            makeMove(move);
        }
    }

    public void makeMove(ChessMove move) throws Exception {
            CheckmateDetector checkmateDetector = new StandardCheckmateDetector(board);

            PieceColor color = board.getTurn() ? PieceColor.BLACK : PieceColor.WHITE;
            if (checkmateDetector.isInCheck(color) || checkmateDetector.isStalemate(color)){
                throw new Exception("checkmate/stalemate no move can be made");
            }



            if(move.getPosition().equals("O-O")){
                Square destination = getSmallCastlingSquare(board.isWhiteTurn());
                Piece king = getKing(board.isWhiteTurn());
                king.move(destination, board);
            }else {

                if(move.getPosition().equals("O-O-O")){
                    Square destination = getBigCastlingSquare(board.isWhiteTurn());
                    Piece king = getKing(board.isWhiteTurn());
                    king.move(destination, board);
                }else {
                    Square destination = getSquare(board, move);
                    Piece piece = getPiece(board, move, destination);
                    piece.move(destination, board);
                }
            }
            PieceColor checkColor = board.getTurn()? PieceColor.WHITE : PieceColor.BLACK;
            if(checkmateDetector.isInCheck(checkColor)){
                throw new Exception("player moved illegal move there is Check");
            }
            board.toggleTurn();
    }

    private Piece getKing(boolean whiteTurn) {
        return whiteTurn ? board.getWhiteKing(): board.getBlackKing();
    }


    private Piece getPiece(Board board, ChessMove move, Square square) {
        if (board.isWhiteTurn()) {
            return board.getWhitePieces().stream()
                    .filter(piece -> move.getPiece() == pieceToChessPiece(piece))
                    .filter(piece -> piece.getLegalMoves(board).contains(square))
                    .filter(piece -> piece.getPosition().getPosition().toAlgebraic().contains(move.getFrom() == null ? "": move.getFrom()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No matching white piece found for move: " + move));
        } else {
            return board.getBlackPieces().stream()
                    .filter(piece -> move.getPiece() == pieceToChessPiece(piece))
                    .filter(piece -> piece.getLegalMoves(board).contains(square))
                    .filter(piece -> piece.getPosition().getPosition().toAlgebraic().contains(move.getFrom() == null ? "": move.getFrom()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No matching black piece found for move: " + move));
        }
    }

    private Square getSquare(Board board, ChessMove move){
        Square[][] squares = board.getSquareArray();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j].getPosition().toAlgebraic().equals(move.getPosition())){
                    return squares[i][j];
                }
            }
        }
        return null;
    }

    private Square getSmallCastlingSquare(boolean isWhiteTurn){
        return isWhiteTurn ? board.getSquare("g1") : board.getSquare("g8");
    }
    private Square getBigCastlingSquare(boolean isWhiteTurn){
        return isWhiteTurn ? board.getSquare("c1") : board.getSquare("c8");
    }

    private ChessPiece pieceToChessPiece(Piece piece){
        return switch (piece){
            case Bishop b -> ChessPiece.BISHOP;
            case King k-> ChessPiece.KING;
            case Queen q -> ChessPiece.QUEEN;
            case Rook r -> ChessPiece.ROOK;
            case Pawn p -> ChessPiece.PAWN;
            case Knight kn -> ChessPiece.KNIGHT;
            case null, default -> throw new IllegalStateException("Unexpected value: " + piece);
        };
    }


}
