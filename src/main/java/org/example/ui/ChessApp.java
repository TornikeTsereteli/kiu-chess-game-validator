package org.example.ui;

import org.example.common.GameSimulator.GameSimulator;
import org.example.model.Board;
import org.example.model.ChessMove;
import org.example.parser.PgnParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class ChessApp {
    public static void main(String[] args) throws Exception {
        PgnParser parser =  new PgnParser();
//        List<String> moveStrings = parser.parse("C:\\Users\\WERO\\IdeaProjects\\ChessGameValidatorProject\\src\\main\\resources\\plays\\dzetki mate.pgn");
        Path path = Paths.get(
                Objects.requireNonNull(
                        ChessApp.class.getClassLoader().getResource("plays/tsereteli vs petriashvili.pgn")
                ).toURI()
        );
        List<String> moveStrings = parser.parse(path.toString());
        List<ChessMove> chessMoves = ChessMove.fromTextList(moveStrings);
        Board board = new Board();
        GameSimulator simulator = new GameSimulator(board); // assumes makeMove() & getBoard() methods
        ChessGameUI ui = new ChessGameUI();
        ui.renderGame(chessMoves, simulator);
    }
}
