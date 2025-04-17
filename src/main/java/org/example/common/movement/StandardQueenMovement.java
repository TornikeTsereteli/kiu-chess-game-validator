package org.example.common.movement;

import org.example.model.Board;
import org.example.model.Piece;
import org.example.model.Square;
import org.example.util.MovementHelper;

import java.util.List;

public class StandardQueenMovement implements MovementStrategy{

    private final Piece piece;


    public StandardQueenMovement(Piece piece) {
        this.piece = piece;
    }

    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        return MovementHelper.getCombinedMoves(chessBoard,piece);
    }

}
