package org.example.common.movement;

import org.example.model.Board;
import org.example.model.Piece;
import org.example.model.Rook;
import org.example.model.Square;
import org.example.model.enums.PieceColor;

import java.util.LinkedList;
import java.util.List;

public class AddCastleMovementStrategy implements MovementStrategy{

    private Piece king;
    private MovementStrategy movementStrategy;

    public AddCastleMovementStrategy(MovementStrategy movementStrategy, Piece king) {
        this.movementStrategy = movementStrategy;
        this.king = king;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> squares = movementStrategy.getLegalMoves(board);

        if (!king.isWasMoved()) {
            if (king.getColor() == PieceColor.WHITE) {
                // Kingside castling
                Square e1 = board.getSquare("e1");
                Square f1 = board.getSquare("f1");
                Square g1 = board.getSquare("g1");
                Square h1 = board.getSquare("h1");
                if (h1.getOccupyingPiece() instanceof Rook
                        && !h1.getOccupyingPiece().isWasMoved()
                        && !f1.isOccupied()
                        && !g1.isOccupied()
                        && !board.isSquareUnderThreat(e1, PieceColor.WHITE)
                        && !board.isSquareUnderThreat(f1, PieceColor.WHITE)
                        && !board.isSquareUnderThreat(g1, PieceColor.WHITE)) {
                    squares.add(g1); // King would move to g1
                }

                // Queenside castling
                Square d1 = board.getSquare("d1");
                Square c1 = board.getSquare("c1");
                Square b1 = board.getSquare("b1");
                Square a1 = board.getSquare("a1");
                if (a1.getOccupyingPiece() instanceof Rook
                        && !a1.getOccupyingPiece().isWasMoved()
                        && !d1.isOccupied()
                        && !c1.isOccupied()
                        && !b1.isOccupied()
                        && !board.isSquareUnderThreat(e1, PieceColor.WHITE)
                        && !board.isSquareUnderThreat(d1, PieceColor.WHITE)
                        && !board.isSquareUnderThreat(c1, PieceColor.WHITE)) {
                    squares.add(c1); // King would move to c1
                }
            } else {
                // Kingside castling
                Square e8 = board.getSquare("e8");
                Square f8 = board.getSquare("f8");
                Square g8 = board.getSquare("g8");
                Square h8 = board.getSquare("h8");
                if (h8.getOccupyingPiece() instanceof Rook
                        && !h8.getOccupyingPiece().isWasMoved()
                        && !f8.isOccupied()
                        && !g8.isOccupied()
                        && !board.isSquareUnderThreat(e8, PieceColor.BLACK)
                        && !board.isSquareUnderThreat(f8, PieceColor.BLACK)
                        && !board.isSquareUnderThreat(g8, PieceColor.BLACK)) {
                    squares.add(g8); // King would move to g8
                }

                // Queenside castling
                Square d8 = board.getSquare("d8");
                Square c8 = board.getSquare("c8");
                Square b8 = board.getSquare("b8");
                Square a8 = board.getSquare("a8");
                if (a8.getOccupyingPiece() instanceof Rook
                        && !a8.getOccupyingPiece().isWasMoved()
                        && !d8.isOccupied()
                        && !c8.isOccupied()
                        && !b8.isOccupied()
                        && !board.isSquareUnderThreat(e8, PieceColor.BLACK)
                        && !board.isSquareUnderThreat(d8, PieceColor.BLACK)
                        && !board.isSquareUnderThreat(c8, PieceColor.BLACK)) {
                    squares.add(c8); // King would move to c8
                }
            }
        }

        return squares;
    }





}
