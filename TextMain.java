import board.*;
import java.util.Scanner;

public class TextMain {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard(true);
        Scanner input = new Scanner(System.in);
        String start;
        String end;

        board.initialize();
        while (true) {
            System.out.println(board);
            try {
                System.out.print("Start Location: ");
                start = input.nextLine();

                System.out.print("End Location: ");
                end = input.nextLine();

                if (board.movePiece(start, end))
                    board.changeTurn();

            } catch (NullPointerException e) {
                System.out.println("Invalid Input\n");
            }
        }
    }
}
