import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

  public class main {

    public static void main(String[] args) {
      try {
        File input = new File("input.txt");
        Scanner scan = new Scanner(input);
        while(scan.hasNextLine()){
          // Do Something
        }
      } catch (FileNotFoundException e) {
        System.out.println(e);
        return;
      }
    }

  }


