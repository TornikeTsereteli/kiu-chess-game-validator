package org.example.common.movement;

import org.example.model.Board;
import org.example.model.Square;

import java.util.List;

public interface MovementStrategy {
    List<Square> getLegalMoves(Board board);
}
