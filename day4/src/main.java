import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {

  public static void main(String[] args) {
    List<List<String[]>> arrayOfBoards = new ArrayList<>();
    List<List<String[]>> arrayOfWinningBoards = new ArrayList<>();

    List<String> numbersToDraw = new ArrayList<>();
    int n = 5; // 5x5
    try {
      File input = new File("input.txt");
      Scanner scan = new Scanner(input);
      // Puts the numbers that we need to draw into an array
      if (scan.hasNextLine()) {
        numbersToDraw.addAll(Arrays.asList(scan.nextLine().split(",")));
      }

      // Initialize the boards
      initBingoBoards(arrayOfBoards, n, scan);

      // Play the Bingo Game
      System.out.println(
          "Result = " + playBingoGame(arrayOfBoards, numbersToDraw, n, arrayOfWinningBoards));

    } catch (FileNotFoundException e) {
      System.out.println(e);
      return;
    }
  }

  private static void initBingoBoards(List<List<String[]>> arrayOfBoards, int n, Scanner scan) {
    while (scan.hasNextLine()) {
      String data = scan.nextLine();
      if (data.equals("")) {
        arrayOfBoards.add(nextBoard(scan, n));
      }
    }
  }

  private static List<String[]> nextBoard(Scanner scan, int n) {
    List<String[]> listOfTuples = new ArrayList<>();
    for (int i = 0; i < n * n; i++) {
      String[] tuple = new String[2];
      tuple[0] = scan.next();
      tuple[1] = "0";
      listOfTuples.add(tuple);
    }
    return listOfTuples;
  }

  private static int playBingoGame(List<List<String[]>> arrayOfBoards,
      List<String> numbersToDraw, int n, List<List<String[]>> arrayOfWinningBoards) {
    int i = 0;
    int numOfBoards = arrayOfBoards.size();
    String recentDraw;
    while (i < numbersToDraw.size() && arrayOfWinningBoards.size() < numOfBoards) {
      recentDraw = numbersToDraw.get(i);
      String[] tuple = new String[2];
      tuple[0] = recentDraw;
      tuple[1] = "0";

      for (List<String[]> board : arrayOfBoards) {
        if (containsTuple(board, tuple)) {
          int indexOfRecentDraw = indexOfTuple(board, tuple);
          // Mark the number on the board
          board.get(indexOfRecentDraw)[1] = "1";
          // Check if it is a winner
          if (isWinner(board, n, indexOfRecentDraw)) {
            arrayOfWinningBoards.add(board);
            printBoard(board,n);
            System.out.println("");
          }
        }
      }
      i++;
      arrayOfBoards.removeAll(arrayOfWinningBoards);
    }
    return sumOfUnmarked(arrayOfWinningBoards.get(arrayOfWinningBoards.size() - 1)) * Integer
        .parseInt(numbersToDraw.get(i - 1));
  }


  private static boolean isWinner(List<String[]> board, int n, int indexOfRecentDraw) {
    int row = indexOfRecentDraw / n;
    int column = indexOfRecentDraw % n;
    boolean rowWin = true;
    boolean columnWin = true;

    // Check row for winner
    for (int i = row * n; i < row * n + 5; i++) {
      if (board.get(i)[1].equals("0")) {
        rowWin = false;
        break;
      }
    }
    // Check column for winner
    for (int i = column; i <= column + (n * (n - 1)); i += n) {
      if (board.get(i)[1].equals("0")) {
        columnWin = false;
        break;
      }
    }
    return columnWin || rowWin;
  }

  private static int sumOfUnmarked(List<String[]> board) {
    int count = 0;
    for (String[] tuple : board) {
      if (tuple[1].equals("0")) {
        count += Integer.parseInt(tuple[0]);
      }
    }
    return count;
  }

  private static void printBingoGame(List<List<String[]>> arrayOfBoards, int n) {
    for (List<String[]> board : arrayOfBoards) {
      printBoard(board, n);
      System.out.println("");

    }
  }

  private static void printBoard(List<String[]> board, int n) {
    String[] tuple;
    for (int i = 0; i < n * n; i++) {
      tuple = board.get(i);
      if (i % n == 4) {
        System.out.print("Tuple[" + tuple[0] + "," + tuple[1] + "]");
        System.out.println("");
      } else {
        System.out.print("Tuple[" + tuple[0] + "," + tuple[1] + "]");

      }
    }
  }

  private static boolean containsTuple(List<String[]> board, String[] tuple) {
    for (String[] tuple2 : board) {
      if (tuple2[0].equals(tuple[0]) && tuple2[1].equals(tuple[1])) {
        return true;
      }
    }
    return false;
  }

  private static int indexOfTuple(List<String[]> board, String[] tuple) {
    for (int i = 0; i < board.size(); i++) {
      String[] tuple2 = board.get(i);
      if (tuple2[0].equals(tuple[0]) && tuple2[1].equals(tuple[1])) {
        return i;
      }
    }
    return -1;
  }


}

