package org.example.model;

import org.example.common.moveExecutor.BasicMoveExecutor;
import org.example.common.moveExecutor.MoveExecutorStrategy;
import org.example.common.movement.MovementStrategy;
import org.example.common.movement.StandardKingMovement;
import org.example.model.enums.PieceColor;

public class King extends Piece {

    public King(PieceColor color, Square initSq) {
        super(color, initSq);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardKingMovement(this);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy()
    {
        return new BasicMoveExecutor(this);

    }


}
