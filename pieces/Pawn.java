package pieces;

import board.ChessBoard;

public class Pawn extends Piece {
    public Pawn() {
        super("w", "Pawn");
        setIconLocation();
        super.setValue(1);
    }

    public Pawn(String color) {
        super(color, "Pawn");
        setIconLocation();
        super.setValue(1);
    }

    public Pawn(Piece p) {
        super(p);
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_plt60.png");
        else
            setIconLocation("/assets/Chess_pdt60.png");
    }

    /**
     * Promotes pawn to piece of choice when on the
     * furthest rank from its side
     *
     * @param board The chess board object
     * @param end   The end location of the pawn
     */
    public void promote(ChessBoard board, String end) {

        int[] endLocation = board.parseLocation(end);
        int xSpot = endLocation[1];
        int ySpot = endLocation[0];
        Piece[][] grid = board.getGrid();

        // If pawn is white and reaches the 8th rank, promote
        if ((getColor().equals("w")) && endLocation[0] == 8) {
            board.removePiece(end);
            board.getGamestate().incrementWScore(8);
            grid[ySpot][xSpot] = new Queen("w");
        }

        // if pawn is black and reaches the 1st rank, promote
        else if ((getColor().equals("w")) && endLocation[0] == 1) {
            board.removePiece(end);
            board.getGamestate().incrementBScore(8);
            grid[ySpot][xSpot] = new Queen("b");
        }
    }
    @Override
    public boolean isLegalMove(ChessBoard board, Movement playersMove)
    {

        if( board.isWayClear(playersMove.start, playersMove.end) && canPieceMoveLegally(board, playersMove.start, playersMove.end, playersMove.color) && getTimesMoved() == 0 && playersMove.yDirection == 2 && playersMove.xDirection == 0 && board.getGrid()[playersMove.endY][playersMove.endX] == null )
            return true;
        else if (board.isWayClear(playersMove.start, playersMove.end) && canPieceMoveLegally(board, playersMove.start, playersMove.end, playersMove.color) && playersMove.yDirection == 1  && playersMove.xDirection == 0 && board.getGrid()[playersMove.endY][playersMove.endX] == null  )
            return true;
        else if(playersMove.yDirection == 1 && Math.abs(playersMove.xDirection) == 1 && board.isWayClear(playersMove.start, playersMove.end) && canPieceMoveLegally(board, playersMove.start, playersMove.end, playersMove.color)  && board.getGrid()[playersMove.endY][playersMove.endX] != null )
            return true;
        else
            return false;
    }


    public String toString() {
        return "Pa(" + getColor() + ")";
    }

}
