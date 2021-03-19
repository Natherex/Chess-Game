package pieces;

import board.ChessBoard;

public class Rook extends Piece {
    public Rook() {
        super("w", "Rook");
        setIconLocation();
        super.setValue(5);
    }

    public Rook(String color) {
        super(color, "Rook");
        setIconLocation();
        super.setValue(5);
    }

    public Rook(Piece p) {
        super(p);
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_rlt60.png");
        else
            setIconLocation("/assets/Chess_rdt60.png");
    }
    public String toString() {
        return "Ro(" + getColor() + ")";
    }


    // Checks to see if the move is legal for a Rook.
    //rooks can move along the x exclusevly or y axis exclusively. Rook cannot move through other pieces, or if king
    //is in check.
    @Override
    public boolean isLegalMove(ChessBoard board, Movement playersMove)
    {
        if( (playersMove.xDirection == 0 || playersMove.yDirection == 0) && board.isWayClear(playersMove.start, playersMove.end) && canPieceMoveLegally(board, playersMove.start, playersMove.end, playersMove.color))
            return true;
        else
            return false;
    }

}
