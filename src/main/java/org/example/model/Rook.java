package org.example.model;

import org.example.common.moveExecutor.BasicMoveExecutor;
import org.example.common.moveExecutor.MoveExecutorStrategy;
import org.example.common.movement.MovementStrategy;
import org.example.common.movement.StandardRookMovement;
import org.example.model.enums.PieceColor;

public class Rook extends Piece {

    public Rook(PieceColor color, Square initSq) {
        super(color, initSq);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardRookMovement(this);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
        return new BasicMoveExecutor(this);
    }


}

