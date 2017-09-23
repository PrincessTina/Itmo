

public class Main {
  public static void main(String[] args) {
    //Method.coefficient = new double[][] {{3, -2, 1}, {5, -14, 15}, {1, 2, -3}};
    //Method.mems = new double[]{0, 0, 0};

    //Method.coefficient = new double[][]{{1, 1, 0, 0, 0}, {1, 1, 1, 0, 0}, {0, 1, 1, 1, 0}, {0, 0, 1, 1, 1}, {0, 0, 0, 1, 1}};
    //Method.mems = new double[]{1, 4, -3, 2, -1};

    Method.coefficient = new double[][]{{1, 2, 3, 4, 5}, {2, 3, 4, 5, 1}, {3, 4, 5, 1, 2}, {1, 3, 5, 12, 9}, {4, 5, 6, -3, 3}};
    Method.mems = new double[]{0, 0, 0, 0, 0};

    Method.calculation();
  }
}
