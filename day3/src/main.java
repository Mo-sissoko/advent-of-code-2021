import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class main {

  public static void main(String[] args) {
    try {
      File input = new File("input.txt");
      Scanner scan = new Scanner(input);
      List<String> oxygenGeneratorRatingList = new ArrayList<>();
      List<String> co2ScrubbingRatingList = new ArrayList<>();

      int numOfBits = 12;
      Map<Integer, int[]> statsOxygen = initStatsMap(numOfBits);
      Map<Integer, int[]> statsCo2 = initStatsMap(numOfBits);
      Map<Integer, int[]> stats = initStatsMap(numOfBits);

      while (scan.hasNextLine()) {
        String data = scan.nextLine();
        oxygenGeneratorRatingList.add(data);
        co2ScrubbingRatingList.add(data);
        updateStatsMap(data, statsOxygen, numOfBits, 1);
        updateStatsMap(data, statsCo2, numOfBits, 1);
        updateStatsMap(data, stats, numOfBits, 1);


      }
      System.out.println(
          "Life Support Rating: " + calculateLifeSupportRating(statsOxygen, statsCo2,
              oxygenGeneratorRatingList,
              co2ScrubbingRatingList, numOfBits));
    } catch (
        FileNotFoundException e) {
      System.out.println(e);
      return;
    }
  }


  static public Map<Integer, int[]> initStatsMap(int numOfBits) {
    Map<Integer, int[]> stats = new HashMap<>();
    for (int i = 1; i <= numOfBits; i++) {
      int[] tuple = new int[2];
      stats.put(i, tuple);
    }
    return stats;
  }

  static public void updateStatsMap(String binary, Map<Integer, int[]> stats, int numOfBits,
      int inc) {
    String[] binarySpiltted = binary.split("");
    for (int i = 1; i <= numOfBits; i++) {
      int[] newTuple = stats.get(i);
      if (binarySpiltted[i - 1].equals("0")) {
        newTuple[0] = newTuple[0] + inc;
      } else {
        newTuple[1] = newTuple[1] + inc;

      }
      stats.replace(i, newTuple);
    }
  }

  static public int calculateGammaAndEpsilonRate(Map<Integer, int[]> stats, int numOfBits) {
    String gammaRate = "";
    String epsilonRate = "";

    for (int i = 1; i <= numOfBits; i++) {
      int[] tuple = stats.get(i);
      if (tuple[1] > tuple[0]) {
        gammaRate += "0";
        epsilonRate += "1";
      } else {
        gammaRate += "1";
        epsilonRate += "0";

      }
    }
    return Integer.parseInt(gammaRate, 2) * Integer.parseInt(epsilonRate, 2);
  }

  static public int calculateLifeSupportRating(Map<Integer, int[]> statsOxygen,
      Map<Integer, int[]> statsCo2,
      List<String> oxygenGeneratorRatingList,
      List<String> co2ScrubbingRatingList, int numOfBits) {

    List<String> oxygenGeneratorRatingListCopy = oxygenGeneratorRatingList;
    List<String> co2ScrubbingRatingListCopy = co2ScrubbingRatingList;
    int i = 1;
    int j = 1;
    while (oxygenGeneratorRatingListCopy.size() != 1) {
      if (i > numOfBits) {
        throw new IllegalArgumentException();
      }
      int[] tuple = statsOxygen.get(i);
      if (tuple[1] >= tuple[0]) {

        if (oxygenGeneratorRatingListCopy.size() > 1) {
          oxygenGeneratorRatingListCopy = filterArray(oxygenGeneratorRatingListCopy, i - 1, "1",
              statsOxygen, numOfBits);
        }

      } else {
        if (oxygenGeneratorRatingListCopy.size() > 1) {
          oxygenGeneratorRatingListCopy = filterArray(oxygenGeneratorRatingListCopy, i - 1, "0",
              statsOxygen, numOfBits);
        }
      }
      i++;
    }

    while (co2ScrubbingRatingListCopy.size() != 1) {
      if (j > numOfBits) {
        throw new IllegalArgumentException();
      }
      int[] tuple = statsCo2.get(j);
      if (tuple[0] <= tuple[1]) {

        if (co2ScrubbingRatingListCopy.size() > 1) {
          co2ScrubbingRatingListCopy = filterArray(co2ScrubbingRatingListCopy, j - 1, "0",
              statsCo2, numOfBits);
        }

      } else {
        if (co2ScrubbingRatingListCopy.size() > 1) {
          co2ScrubbingRatingListCopy = filterArray(co2ScrubbingRatingListCopy, j - 1, "1",
              statsCo2, numOfBits);
        }
      }
      j++;
    }
    return Integer.parseInt(co2ScrubbingRatingListCopy.get(0), 2) * Integer
        .parseInt(oxygenGeneratorRatingListCopy.get(0), 2);
  }

  static public List<String> filterArray(List<String> diagnosticReport, int bitPosition,
      String bit, Map<Integer, int[]> stats, int numOfBits) {
    List<String> filteredDiagnosticReport = new ArrayList<>(diagnosticReport);
    for (String binaryNum : diagnosticReport) {
      if (filteredDiagnosticReport.size() == 1) {
        break;
      }
      if (!binaryNum.substring(bitPosition, bitPosition + 1).equals(bit)) {
        filteredDiagnosticReport.remove(binaryNum);
        updateStatsMap(binaryNum, stats, numOfBits, -1);
      }
    }
    return filteredDiagnosticReport;
  }

}
