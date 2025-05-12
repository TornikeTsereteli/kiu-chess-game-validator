package org.example;

import org.example.common.GameSimulator.GameSimulator;
import org.example.common.GameSimulator.GameSimulatorBase;
import org.example.model.Board;
import org.example.model.ChessMove;
import org.example.parser.PgnParser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {
        PgnParser parser = new PgnParser();
        ChessMove chessMove = new ChessMove();

        File[] pgnFiles = loadPGNFiles("plays");
        if (pgnFiles == null || pgnFiles.length == 0) {
            System.out.println("No PGN files found in resources/plays.");
            return;
        }

        simulateAllGamesFromFiles(parser, chessMove, pgnFiles);
    }

    private static File[] loadPGNFiles(String resourceDir) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL playsDirUrl = classLoader.getResource(resourceDir);

        if (playsDirUrl == null) {
            System.err.println("Directory '" + resourceDir + "' not found in resources.");
            return null;
        }

        File playsDir = new File(playsDirUrl.toURI());
        return playsDir.listFiles((dir, name) -> name.endsWith(".pgn"));
    }

    private static void simulateAllGamesFromFiles(PgnParser parser, ChessMove chessMove, File[] pgnFiles) {
        for (File pgnFile : pgnFiles) {
            System.out.println("Processing file: " + pgnFile.getName());

            List<List<String>> games;
            try {
                games = parser.parse(pgnFile.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Failed to parse PGN file: " + pgnFile.getName());
                System.err.println("Error: " + e.getMessage());
                continue;
            }

            int gameNumber = 1;
            for (List<String> moveStrings : games) {
                System.out.printf("Simulating game #%d from file: %s%n", gameNumber++, pgnFile.getName());

                try {
                    simulateSingleGame(chessMove, moveStrings);
                    System.out.println("Game simulation complete.");
                } catch (Exception e) {
                    System.err.printf("Game #%d in file '%s' failed due to logic error.%n", gameNumber - 1, pgnFile.getName());
                    System.err.println("Error: " + e.getMessage());
                }

                System.out.println("------------------------------------------------");
            }
        }
    }

    private static void simulateSingleGame(ChessMove chessMove, List<String> moveStrings) throws Exception {
        List<ChessMove> moves = chessMove.toStructuredMoves(moveStrings);
        Board board = new Board();
        GameSimulatorBase simulator = new GameSimulator(board);
        simulator.SimulateGame(moves);
    }
}
