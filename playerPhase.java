//import java.util.Scanner;
//import pieces.Piece;
//
//public class playerPhase{
//
//  private String playerNumber;
//  private int piecesLeft;
//  private int bishopsLeft;
//  private int rooksLeft;
//  private int knightsLeft;
//  private int queensLeft;
//  private int pawnsLeft;
//
//  public playerPhase(){
//
//    this.playerNumber = "One";
//    this.piecesLeft = 16;
//
//  }
//
//  public playerPhase(String number){
//
//    this.playerNumber = number;
//    this.piecesLeft = 16;
//
//  }
//
//  public String getPlayer(){
//
//    return ("Player " + playerNumber);
//
//  }
//
//  public int currentPieces(){
//
//    return piecesleft;
//
//  }
//
//  public String choosePiece(){
//
//    System.out.print("Select piece to move: ");
//    Scanner choosePiece = new Scanner(System.in);
//    String currentPosition = choosePiece;
//    return currentPosition.toUpperCase();
//
//  }
//
//  //use isValidLocation(string)
//  public String placeToMove(){
//
//    System.out.print("Select space to move piece to: ");
//    Scanner boardSpace = new Scanner(System.in);
//    String newPosition = boardSpace;
//    return newPosition.toUpperCase();
//
//  }
//
//  /**
//   * Updates the total pieces remaining for the player
//   * each time a piece is captured depending on piece
//   * @param board the ChestBoard object
//   * @param piece the location of the piece being removed
//   */
//  public void updatePiecesLeft(ChessBoard board, String piece) {
//
//    int[] endLocation = board.parseLocation(end);
//    String pieceName = endLocation[0][1].getName();
//
//    if (pieceName.equals("Queen")) {
//      this.queensLeft = 0;
//      this.piecesLeft -= 1;
//
//    }
//
//    else if (pieceName.equals("Knight")) {
//      this.knightsLeft -= 1;
//      this.piecesLeft -= 1;
//
//    }
//
//    else if (pieceName.equals("Bishop")) {
//
//      this.bishopsLeft -= 1;
//      this.piecesLeft -=1;
//
//    }
//
//    else if (pieceName.equals("Rook")) {
//      this.rooksLeft -= 1;
//      this.piecesLeft -=1;
//
//    }
//
//    else if (pieceName.equals("Pawn")) {
//      this.pawnsLeft -= 1;
//      this.piecesLeft -=1;
//
//    }
//
//  }
//
//}
