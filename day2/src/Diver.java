public class Diver {

  private int horizontal_pos;
  private int depth;
  private int aim;
  public Diver(){
    horizontal_pos = 0;
    depth = 0;
    aim = 0;
  }

  void handleCommands(String command) {
    String[] commandArray = command.split(" ");
    int val = Integer.parseInt(commandArray[1]);
    switch (commandArray[0]) {
      case "forward":
        horizontal_pos += val;
        depth += aim * val;
        break;
      case "up":
        aim -= val;
        break;
      case "down":
        aim += val;
        break;
      default:
        throw new IllegalArgumentException("Invalid Command");
    }

  }

  void printStats(){
    System.out.println("Horizontal Position: " + horizontal_pos);
    System.out.println("Depth: " + depth);
    System.out.println("Result: " + (depth * horizontal_pos));

  }

}
