import static org.junit.Assert.*;
import org.junit.Test;

public class QueenTest {

    @Test
    public void moveQueenTest_1() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Queen", "w", "B4");
        board.placePiece("Queen", "b", "G4");

        assertTrue("Moved white Queen from B4 to F8", board.movePiece("B4", "F8"));
        assertFalse("Moved nothing from B4 to F8", board.movePiece("B4", "F8"));

        board.changeTurn();

        assertTrue("Moved black Queen from G4 to D1", board.movePiece("G4", "D1"));
        assertFalse("Moved nothing from G4 to D1", board.movePiece("G4", "D1"));

    }

    @Test
    public void moveQueenTest_2() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Queen", "w", "B4");
        board.placePiece("Queen", "b", "H3");

        assertFalse("White Queen should not be able to move from B4 to C8", board.movePiece("B4", "C8"));

        board.changeTurn();

        assertFalse("Black Queen should not be able to move from H3 to A8", board.movePiece("H3", "A8"));

    }

    @Test
    public void moveQueenTest_3() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Queen", "w", "A4");
        board.placePiece("Queen", "b", "F7");

        assertTrue("Moved white Queen from A4 to H4", board.movePiece("A4", "H4"));
        assertFalse("Moved nothing from A4 to H4", board.movePiece("A4", "H4"));

        board.changeTurn();

        assertTrue("Moved black Queen from F7 to F8", board.movePiece("F7", "F8"));
        assertFalse("Moved nothing from F7 to F8", board.movePiece("F7", "F8"));

    }

    @Test
    public void moveQueenTest_4() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Queen", "w", "A4");
        board.placePiece("Queen", "b", "G7");

        assertFalse("White Queen should not be able to move from A4 to B8", board.movePiece("A4", "B8"));

        board.changeTurn();

        assertFalse("Black Queen should not be able to move from G8 to H1", board.movePiece("G8", "H1"));

    }

    @Test
    public void cannotMoveQueenTest_1() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Queen", "w", "A5");
        board.placePiece("Rook", "b", "A7");
        board.placePiece("Queen", "b", "E8");
        board.placePiece("Rook", "w", "H8");

        assertFalse("White Queen cannot move from A5 as King will be in check", board.movePiece("A5", "B4"));

        board.changeTurn();

        assertFalse("Black Queen cannont move from E5 as King will be in check", board.movePiece("E5", "F6"));

    }

    @Test
    public void cannotMoveQueenTest_2() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Queen", "w", "A5");
        board.placePiece("Rook", "b", "A7");
        board.placePiece("Queen", "b", "E8");
        board.placePiece("Rook", "w", "H8");

        assertFalse("White Queen cannot move from A5 as King will be in check", board.movePiece("A5", "H5"));

        board.changeTurn();

        assertFalse("Black Queen cannont move from E8 as King will be in check", board.movePiece("E8", "E2"));

    }

    @Test
    public void killQueenTest_1() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Queen", "w", "B6");
        board.placePiece("Queen", "b", "B2");
        board.placePiece("Queen", "w", "E6");
        board.placePiece("Queen", "b", "H6");

        assertTrue("White Queen should be able to kill black Queen on B2 from B6", board.movePiece("B6", "B2"));

        board.changeTurn();

        assertTrue("Black Queen should be able to kill white Queen on E6 from H6", board.movePiece("H6", "E6"));
    }

    @Test
    public void killQueenTest_2() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Queen", "w", "C7");
        board.placePiece("Queen", "b", "G3");
        board.placePiece("Queen", "w", "H6");
        board.placePiece("Queen", "b", "E3");

        assertTrue("White Queen should be able to kill black Queen on G3", board.movePiece("C7", "G3"));

        board.changeTurn();

        assertTrue("Black Queen should be able to kill white Queen on H6", board.movePiece("E3", "H6"));

    }

}