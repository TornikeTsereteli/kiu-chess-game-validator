# PGN Validator and Chess Game Simulator

This project is a Java-based PGN (Portable Game Notation) validator and simulator designed to validate the syntax and simulate chess games from PGN files. Additionally, it provides a simple graphical interface to visualize and play through the games.

## 🚀 Features

- ✅ **PGN Parsing & Syntax Validation**
    - Robust PGN parser that supports standard chess notation.
    - Validates move structure, tags, and syntax according to PGN specification.

- ♟️ **Game Simulation**
    - Accurately simulates a chess game move-by-move from a valid PGN.
    - Custom chess engine built to interpret and enforce move legality.
    - Supports full chess rules including capturing, promotion, castling, and en passant.

- 🧠 **Legal Move Generation**
    - Each piece (King, Queen, Rook, Bishop, Knight, Pawn) has its own movement logic following chess rules.
    - Collision detection and capturing handled based on piece color.
    - King safety checks are incorporated (e.g. no move into check).

- 👑 **Castling Support**
    - Validates and simulates both kingside and queenside castling moves.
    - Ensures castling conditions are met (e.g. no check, no pieces in between, rook and king unmoved).

- 🎨 **Graphical Interface (ChessUI)**
    - UI built using JavaFX (or Swing).
    - Displays board state after each move from the PGN file.
    - Allows the user to visually follow the game.

## 🧱 Codebase Philosophy

Most of the main model classes and core business logic were adapted from a previously completed **refactoring task**, which involved designing a clean and maintainable chess engine architecture. As a result:

- The code follows **SOLID principles**, favoring high cohesion and low coupling.
- Clear separation of concerns between **parsing**, **simulation**, and **presentation (UI)** layers.
- Extensible object-oriented design — adding new features (e.g., support for new PGN tags or UI enhancements) is straightforward.

This approach ensures the system is **modular**, **testable**, and easy to maintain, making it ideal for both educational and extendable purposes.

## 🏗️ Architecture

The architecture follows clean object-oriented principles with a modular structure. The core logic is decoupled from UI, enabling easy testing and reuse.

### Core Modules

- **`chess.model`**
    - Contains the main game logic, board structure, pieces, and rule enforcement.
    - Each piece (e.g., `King`, `Queen`) extends a base `Piece` class.
    - `Board` class handles game state and move execution.

- **`parser`**
    - Handles reading and parsing PGN files.
    - Performs syntax validation before simulation begins.

- **`simulator`**
    - Takes parsed moves and executes them on the board.
    - Verifies legality of each move and updates the game state accordingly.

- **`ChessUI`**
    - Displays the game using a chessboard GUI.
    - Automatically steps through the game move-by-move from a PGN.

### Testing

- JUnit-based test suite under `pieceMovement` and other test packages.
- Tests include:
    - Piece-specific movement (including edge cases like blocked paths and captures).
    - King movement and castling.
    - Game rule enforcement (e.g., no illegal king moves).


#  run the ChessApp class: 

it shows exactly pgn is transformed and simulated on board