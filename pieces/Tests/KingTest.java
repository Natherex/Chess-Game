import static org.junit.Assert.*;
import org.junit.Test;

public class KingTest {

    @Test
    public void moveKingTest_1() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");

        assertTrue("Moved white king from A1 to A2", board.movePiece("A1", "A2"));
        assertFalse("Moved nothing from A1 to A2", board.movePiece("A1", "A2"));

        board.changeTurn();

        assertTrue("Moved black king from A8 to A7", board.movePiece("A8", "A7"));
        assertFalse("Moved nothing from A8 to A7", board.movePiece("A8", "A7"));

    }

    @Test
    public void moveKingTest_2() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");

        assertFalse("White king should not be able to move from A1 to B5", board.movePiece("A1", "B5"));

        board.changeTurn();

        assertFalse("Black king should not be able to move from A8 to B3", board.movePiece("A8", "B3"));

    }

    @Test
    public void moveKingTest_3() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");

        assertTrue("Moved white king from A1 to B2", board.movePiece("A1", "B2"));
        assertFalse("Moved nothing from A1 to B2", board.movePiece("A1", "B2"));

        board.changeTurn();

        assertTrue("Moved black king from A8 to B7", board.movePiece("A8", "B7"));
        assertFalse("Moved nothing from A8 to B7", board.movePiece("A8", "B7"));
    }

    @Test
    public void castleKingLeft() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "E8");
        board.placePiece("King", "w", "E1");
        board.placePiece("Rook", "w", "A1");

        assertTrue("White king can castle left if Rook and King haven't moved", board.movePiece("E1", "C1"));

    }

    @Test
    public void castleKingRight() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "E8");
        board.placePiece("King", "w", "E1");
        board.placePiece("Rook", "b", "H8");
        board.placePiece("Rook", "w", "H1");

        assertTrue("White king can castle right if Rook and King haven't moved", board.movePiece("E1", "G1"));

    }

    @Test
    public void cannotMoveKing() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "E8");
        board.placePiece("King", "w", "E1");
        board.placePiece("Queen", "b", "F3");
        board.placePiece("Queen", "w", "D6");

        assertFalse("White king cannot move to F2 as it will put him in check", board.movePiece("E1", "F2"));

        board.changeTurn();

        assertFalse("Black king cannot move to F7 as it will put him in check", board.movePiece("E8", "D7"));
        
    }

}