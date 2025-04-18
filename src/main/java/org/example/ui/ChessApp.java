package org.example.ui;

import org.example.common.GameSimulator.GameSimulator;
import org.example.model.Board;
import org.example.model.ChessMove;
import org.example.parser.PgnParser;

import java.util.List;

public class ChessApp {
    public static void main(String[] args) throws Exception {
        PgnParser parser =  new PgnParser();
        List<String> moveStrings = parser.parse("C:\\Users\\WERO\\IdeaProjects\\ChessGameValidatorProject\\src\\main\\resources\\plays\\dzetki mate.pgn");
        List<ChessMove> chessMoves = ChessMove.fromTextList(moveStrings);
        Board board = new Board();
        GameSimulator simulator = new GameSimulator(board); // assumes makeMove() & getBoard() methods
        ChessGameUI ui = new ChessGameUI();
        ui.renderGame(chessMoves, simulator);
    }
}
