package org.example.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class PgnParser {

    private static final String TAG_LINE_REGEX = "^(\\[[A-Z]\\w+ \"[^\"]+\"\\])$";
    private static final String MOVE_NUMBER_REGEX = "(?<=^|\\s)(\\d+)\\.(?=\\s)";
    private static final String SAN_MOVE_REGEX = "^(?:" +
            "O-O(?:-O)?[+#]?" +                                // Castling
            "|" +
            "[KQRBN](?:[a-h]|[1-8]|[a-h][1-8])?x?[a-h][1-8][+#]?" + // Piece moves
            "|" +
            "[a-h]x[a-h][1-8](?:=[QRBN])?[+#]?" +               // Pawn capture
            "|" +
            "[a-h][1-8](?:=[QRBN])?[+#]?" +                     // Pawn move
            ")$";

    public List<List<String>> parse(String filePath) throws IOException {
//        List<String> allLines = readAllLines(filePath);
        String pgnContent = Files.readString(Path.of(filePath));
        List<String> games = splitGames(pgnContent);


        return games.stream().map(game->{
            List<String> allLines = List.of(game.split("\n"));
            List<String> tagSection = extractTagSection(allLines);
            List<String> moveSection = extractMoveSection(allLines);
            validateTags(tagSection);
            String cleanedMoveText = cleanMoveText(moveSection);
            validateMoveNumbers(cleanedMoveText);
            List<String> moves = extractMoves(cleanedMoveText);
            validateMoves(moves);
            return moves;
        }).toList();

    }

    private static List<String> splitGames(String pgnContent) {
        List<String> games = new ArrayList<>();

        // Split on the pattern of new game headers starting with [Event
        String[] rawGames = pgnContent.split("(?=\\[Event )");

        for (String game : rawGames) {
            String trimmed = game.trim();
            if (!trimmed.isEmpty()) {
                games.add(trimmed);
            }
        }

        return games;
    }

    private List<String> readAllLines(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    private List<String> extractTagSection(List<String> lines) {
        List<String> tags = new ArrayList<>();
        for (String line : lines) {
            line = line.strip();
            if (line.isEmpty()) break;
            tags.add(line);
        }
        return tags;
    }

    private List<String> extractMoveSection(List<String> lines) {
        boolean tagEnded = false;
        List<String> moveLines = new ArrayList<>();
        for (String line : lines) {
            if (!tagEnded && line.isBlank()) {
                tagEnded = true;
                continue;
            }
            if (tagEnded) {
                moveLines.add(line.strip());
            }
        }
        return moveLines;
    }

    private void validateTags(List<String> tagLines) {
        for (String tag : tagLines) {
            if (!Regex.match(TAG_LINE_REGEX, tag)) {
                throw new IllegalArgumentException("Invalid PGN tag format: " + tag);
            }
        }
    }

    private String cleanMoveText(List<String> moveLines) {
        String text = String.join(" ", moveLines);
        text = text.replaceAll("\\{[^}]*\\}", ""); // remove comments
        text = text.replaceAll("(1-0|0-1|1/2-1/2|\\*)", ""); // remove results
        text = text.replaceAll(MOVE_NUMBER_REGEX, ""); // remove move numbers
        return text.trim();
    }

    private void validateMoveNumbers(String moveText) {
        Matcher matcher = Regex.getMatcher(MOVE_NUMBER_REGEX, String.join(" ", moveText));
        int expected = 1;
        while (matcher.find()) {
            int actual = Integer.parseInt(matcher.group(1));
            if (actual != expected) {
                throw new IllegalArgumentException("Expected move number " + expected + " but found " + actual);
            }
            expected++;
        }
    }

    private List<String> extractMoves(String cleanedMoveText) {
        return Arrays.stream(cleanedMoveText.split("\\s+"))
                .filter(move -> !move.isBlank())
                .map(move -> move.replaceAll("^\\d+\\.+", "")) // remove leading move numbers like 1. or 23...
                .collect(Collectors.toList());
    }



    private void validateMoves(List<String> moves) {
        for (String move : moves) {
            if (!Regex.match(SAN_MOVE_REGEX, move)) {
                throw new IllegalArgumentException("Invalid SAN move: " + move);
            }
        }
    }
}