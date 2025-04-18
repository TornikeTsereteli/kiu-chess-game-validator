package org.example;

import org.example.common.GameSimulator.GameSimulator;
import org.example.common.GameSimulator.GameSimulatorBase;
import org.example.model.Board;
import org.example.model.ChessMove;
import org.example.parser.PgnParser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

        public static void main(String[] args) throws Exception {
            PgnParser parser = new PgnParser();
            ChessMove chessMove = new ChessMove();

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL playsDirUrl = classLoader.getResource("plays");

            if (playsDirUrl == null) {
                System.err.println("Directory 'plays' not found in resources.");
                return;
            }

            File playsDir = new File(playsDirUrl.toURI());
            File[] pgnFiles = playsDir.listFiles((dir, name) -> name.endsWith(".pgn"));

            if (pgnFiles == null || pgnFiles.length == 0) {
                System.out.println("No PGN files found in resources/plays");
                return;
            }

            for (File pgnFile : pgnFiles) {
                System.out.println("Simulating game from: " + pgnFile.getName());

                List<String> moves = parser.parse(pgnFile.getAbsolutePath());
                List<ChessMove> chessMoves = chessMove.toStructuredMoves(moves);

                Board board = new Board();
                GameSimulatorBase gameSimulatorBase = new GameSimulator(board);
                gameSimulatorBase.SimulateGame(chessMoves);

                System.out.println("Game finished: " + pgnFile.getName());
                System.out.println("------------------------------------------------");
            }
        }

    }

