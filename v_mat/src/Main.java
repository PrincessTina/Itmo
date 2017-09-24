

public class Main {
  public static void main(String[] args) {
    Interface.createInterface();
  }

  static void allocation() {
    Method.coefficient = new double[][] {{3, -2, 1}, {5, -14, 15}, {1, 2, -3}};
    Method.mems = new double[]{0, 0, 0};

    //Method.coefficient = new double[][]{{1, 1, 0, 0, 0}, {1, 1, 1, 0, 0}, {0, 1, 1, 1, 0}, {0, 0, 1, 1, 1}, {0, 0, 0, 1, 1}};
    //Method.mems = new double[]{1, 4, -3, 2, -1};

    //Method.coefficient = new double[][]{{1, -1, 3, 1, 1, 5, 6}, {1, -1, 1, 3, -3, 2, -3}, {1, -2, 0, 4, 0, -3, -9},
      //  {1, 0, -1, 2, -1, 9, 5}, {1, 3, -7, -1, 5, 0, 2}, {8, 21, 0, -2, 4, 17, 10}, {11, 2, -1, 3, 7, 9, 12}};
    //Method.mems = new double[]{0, 0, 0, 9, 0, 11, 0};
  }
}
