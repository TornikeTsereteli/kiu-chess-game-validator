package org.example.ui;

import org.example.common.GameSimulator.GameSimulator;
import org.example.model.ChessMove;

import javax.swing.*;
import java.util.List;

public class ChessGameUI {
    private JFrame frame;
    private ChessBoardPanel boardPanel;

    public ChessGameUI() {
        frame = new JFrame("Chess Board");
        boardPanel = new ChessBoardPanel();
        frame.add(boardPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void renderGame(List<ChessMove> moves, GameSimulator gameSimulator) throws Exception {
        for (ChessMove move : moves) {
            gameSimulator.makeMove(move);
            boardPanel.setBoard(gameSimulator.getBoard());
            Thread.sleep(500); // slow down for visual effect
        }
    }
}
