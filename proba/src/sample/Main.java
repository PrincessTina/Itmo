package sample;

public class Main {

  public static void main(String[] args) {
    int height = 8;
    int width = 2;
    int array[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    int size = width * height;
    int k = 0, j = 0;

    int massiv[][] = new int[height][width];
    int massiv2[][] = new int[width][height];

    for (int i = 0; i < height; i ++) {
      while (k != width) {
        massiv[i][k] = array[j];
        k ++;
        j++;
      }
     k = 0;
    }

    for (int i = 0; i < height; i++) {
      while (k != width) {
        massiv2[k][height - i - 1] = massiv[i][k];
        k ++;
      }
      k = 0;
    }

    for (int i = 0; i < width; i ++) {
      for (int g = 0; g < height; g ++) {
        System.out.print(massiv2[i][g] + " ");
      }
      System.out.println();
    }

    //System.out.println(massiv[2][1]);
    //printArray(array);
  }

  static void logic(int array[], int i) {
    int s = array.length;

    for (int j = 2; j >= 0; j--) {
      int t = array[(s - 1) - i - j];
      array[(s - 1) - i - j] = array[i + (2 - j)];
      array[i + (2 - j)] = t;
    }
  }

  static void printArray(int array[]) {
    int k = 0;
    System.out.println();

    for (int a : array) {
      if (k == 2) {
        System.out.println();
        k = 0;
      }
      System.out.print(a + " ");
      k++;
    }
  }
}
