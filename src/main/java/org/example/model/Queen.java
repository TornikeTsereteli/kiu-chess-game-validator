package org.example.model;

import org.example.common.moveExecutor.MoveExecutorStrategy;
import org.example.common.movement.MovementStrategy;
import org.example.model.enums.PieceColor;

public class Queen extends Piece {

    public Queen(PieceColor color, Square initSq) {
        super(color, initSq);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {

//        return new StandardQueenMovement(this);
        return null;
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
//        return new BasicMoveExecutor(this);
        return null;
    }


}