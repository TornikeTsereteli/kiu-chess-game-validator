package org.example;

import org.example.common.GameSimulator.GameSimulator;
import org.example.common.GameSimulator.GameSimulatorBase;
import org.example.model.Board;
import org.example.model.ChessMove;
import org.example.parser.PgnParser;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        PgnParser parser = new PgnParser();
        ChessMove chessMove = new ChessMove();

        List<String> moves = parser.parse("C:\\Users\\WERO\\IdeaProjects\\ChessGameValidatorProject\\src\\main\\resources\\plays\\tsereteli vs petriashvili.pgn");

//        List<ChessMove> chessMoves = chessMove.toStructuredMoves(moves);
        List<ChessMove> chessMoves = chessMove.toStructuredMoves(List.of("e4","e5","Nf3","Nf6"));

        Board board = new Board();
        GameSimulatorBase gameSimulatorBase = new GameSimulator(board);

        gameSimulatorBase.SimulateGame(chessMoves);

    }
}
