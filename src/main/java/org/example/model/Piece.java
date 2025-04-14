package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.example.common.moveExecutor.MoveExecutorStrategy;
import org.example.common.movement.MovementStrategy;
import org.example.model.enums.PieceColor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Piece {
    @Getter
    private final PieceColor color;
    @Getter
    @Setter
    private Square position;

    @Setter
    private BufferedImage img;
    @Setter
    private MovementStrategy movementStrategy;

    @Setter
    private MoveExecutorStrategy moveExecutorStrategy;

    public Piece(PieceColor color, Square initSq) {
        this.color = color;
        this.position = initSq;


        this.movementStrategy = getMovementStrategy();
        this.moveExecutorStrategy = getMoveExecutorStrategy();
    }

    public boolean move(Square destination, Board board) {
        return moveExecutorStrategy.executeMove(board, destination );
    }

    protected abstract MovementStrategy getMovementStrategy();

    protected abstract MoveExecutorStrategy getMoveExecutorStrategy();


    public Image getImage() {
        return img;
    }

    public List<Square> getLegalMoves(Board b) {
        return movementStrategy.getLegalMoves(b);
    }


}