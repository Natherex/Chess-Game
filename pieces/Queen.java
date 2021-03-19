package pieces;

import board.ChessBoard;

public class Queen extends Piece {

    public Queen() {
        super("w", "Queen");
        setIconLocation();
        super.setValue(9);
    }

    public Queen(String color) {
        super(color, "Queen");
        setIconLocation();
        super.setValue(9);
    }

    public Queen(Piece p) {
        super(p);
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_qlt60.png");
        else
            setIconLocation("/assets/Chess_qdt60.png");
    }

    public String toString() {
        return "Qu(" + getColor() + ")";
    }

    @Override
    public boolean isLegalMove(ChessBoard board, Movement playersMove) {
        String colors = board.getGrid()[playersMove.startY][playersMove.startX].getColor();
        if ((Math.abs(playersMove.xDirection) == Math.abs(playersMove.yDirection)) && board.isWayClear(playersMove.start, playersMove.end) && canPieceMoveLegally(board, playersMove.start, playersMove.end, colors))
            return true;
        else if ((playersMove.xDirection == 0 || playersMove.yDirection == 0) && board.isWayClear(playersMove.start, playersMove.end) && canPieceMoveLegally(board, playersMove.start, playersMove.end, colors))
            return true;
        else {
            return false;
        }
    }
}
