package org.example.common.movement;

import org.example.model.Board;
import org.example.model.Piece;
import org.example.model.Square;
import org.example.util.MovementHelper;

import java.util.List;

public class StandardBishopMovement implements MovementStrategy{
    private final Piece bishop;

    public StandardBishopMovement(Piece piece) {
        this.bishop = piece;
    }

    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        return MovementHelper.getDiagonalMoves(chessBoard, bishop);
    }
}
