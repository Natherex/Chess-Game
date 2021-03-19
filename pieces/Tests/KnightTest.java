import static org.junit.Assert.*;
import org.junit.Test;

public class KnightTest {

    @Test
    public void moveKnightTest_1() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Knight", "w", "E7");
        board.placePiece("Knight", "b", "D3");

        assertTrue("Moved white knight from E7 to F5", board.movePiece("E7", "F5"));
        assertFalse("Moved nothing from E7 to F5", board.movePiece("E7", "F5"));

        board.changeTurn();

        assertTrue("Moved black knight from D3 to B2", board.movePiece("D3", "B2"));
        assertFalse("Moved nothing from D3 to B2", board.movePiece("D3", "B2"));

    }

    @Test
    public void moveKnightTest_2() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Knight", "w", "B7");
        board.placePiece("Knight", "b", "G3");

        assertFalse("White knight should not be able to move from B7 to B8", board.movePiece("B7", "B8"));

        board.changeTurn();

        assertFalse("Black knight should not be able to move from G3 to H4", board.movePiece("G3", "H4"));

    }

    @Test
    public void killKnightTest() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Knight", "w", "E2");
        board.placePiece("Knight", "b", "C3");
        board.placePiece("Knight", "w", "D7");
        board.placePiece("Knight", "b", "F8");

        assertTrue("White knight should be able to kill black knight on C3 from E2", board.movePiece("E2", "C3"));

        board.changeTurn();

        assertTrue("Black knight should be able to kill white knight on F8 from D7", board.movePiece("F8", "D7"));

    }

    @Test
    public void cannotMoveKnightTest() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Knight", "w", "A5");
        board.placePiece("Rook", "b", "A7");
        board.placePiece("Knight", "b", "E8");
        board.placePiece("Rook", "w", "H8");

        assertFalse("White knight cannot move from A5 as King will be in check", board.movePiece("A5", "C4"));

        board.changeTurn();

        assertFalse("Black knight cannont move from E8 as King will be in check", board.movePiece("E8", "G7"));
    }

}