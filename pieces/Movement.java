package pieces;

import board.ChessBoard;

public class Movement{

    protected int[] totalDistance;
    protected int xDirection;
    protected int yDirection;
    protected int[] startCoordinate;
    protected int startY;
    protected int startX;
    protected int[] endCoordinate;
    protected int endY;
    protected int endX;
    protected String color;
    protected String start;
    protected String end;

    protected Movement(ChessBoard board, String starting, String ending) {

        totalDistance = board.distance(starting, ending);
        if (totalDistance != null) {
            start = starting;
            end = ending;
            xDirection = totalDistance[1];
            yDirection = totalDistance[0];
            startCoordinate = board.parseLocation(start);
            startY = startCoordinate[0];
            startX = startCoordinate[1];

            endCoordinate = board.parseLocation(end);
            endY = endCoordinate[0];
            endX = endCoordinate[1];
            String color = board.getGrid()[startY][startX].getColor();
            if(color == null)
                color = "w";
        }
    }
}