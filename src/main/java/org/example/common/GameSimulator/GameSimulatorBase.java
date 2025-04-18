package org.example.common.GameSimulator;

import org.example.model.ChessMove;

import java.util.List;

public interface GameSimulatorBase {
    void SimulateGame(List<ChessMove> moves) throws Exception;
}
