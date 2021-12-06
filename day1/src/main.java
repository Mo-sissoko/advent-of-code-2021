import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

  public static void main(String[] args) {
    try {
      File input = new File("input.txt");
      Scanner scan = new Scanner(input);
      System.out.println(nMeasurementSlidingWindow(scan, 3));
    } catch (FileNotFoundException e) {
      System.out.println(e);
      return;
    }
  }

  static int nMeasurementSlidingWindow(Scanner scan, int windowSize) {
    int count = 0;
    List<Integer> currentArray = new ArrayList<Integer>();
    List<Integer> prevArray = new ArrayList<Integer>();
    while (scan.hasNextLine()) {
      String data = scan.nextLine();

      if (prevArray.size() < windowSize) {
        prevArray.add(Integer.parseInt(data));
      } else {
        for (int i = 1; i < windowSize; i++) {
          currentArray.add(prevArray.get(i));
        }
        currentArray.add(Integer.parseInt(data));
        if (sumElements(currentArray) > sumElements(prevArray)) {
          count++;
        }
        prevArray = currentArray;
        currentArray = new ArrayList<>();
      }
    }
    scan.close();
    return count;
  }

  static int sumElements(List<Integer> array) {
    int sum = 0;
    for (int i : array) {
      sum += i;
    }
    return sum;
  }
}

