
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.*;


class Method {
  static double[][] coefficient;
  static double[] mems;
  private static int n = 0;
  private static HashMap<Integer, String> map = new HashMap<>();

  static void calculation() {
    System.out.println("Исходная матрица:");
    writeMatrix();

    directRate();

    reverseRate();
  }

  private static void directRate() {
    double[] temporary_a = new double[n];
    double temporary_b;

    for (int k = 0; k < n - 1; k++) {
      System.out.println("Новая матрица" + k + ":");

      for (int i = k + 1; i < n; i++) {

        if (coefficient[k][k] == 0) {
          for (int j = k + 1; j < n; j++) {

            if (coefficient[j][k] != 0) {

              temporary_b = mems[k];
              for (int m = 0; m < n; m++) {
                temporary_a[m] = coefficient[k][m];
              }

              mems[k] = mems[j];
              for (int m = 0; m < n; m++) {
                coefficient[k][m] = coefficient[j][m];
              }

              mems[j] = temporary_b;
              for (int m = 0; m < n; m++) {
                coefficient[j][m] = temporary_a[m];
              }

              break;
            }
          }
        }

        if (coefficient[k][k] == 0) {
          continue;
        }

        double consta = -coefficient[i][k] / coefficient[k][k];

        for (int j = 0; j < n; j++) {
          coefficient[i][j] = coefficient[i][j] + coefficient[k][j] * consta;
        }
        mems[i] = mems[i] + mems[k] * consta;
      }
      writeMatrix();
    }

    findDeterminant();
  }

  private static void reverseRate() {
    ArrayList<Double> x_array = new ArrayList<>();
    HashMap<Integer, Double> additional_y = new HashMap<>();

    for (int k = n - 1; k > -1; k--) {
      int i = 1;
      double x = mems[k] / coefficient[k][k];

      if (x_array.size() > 0) {
        for (double y : x_array) {
          if (y == 1000000.0) {
            if (coefficient[k][n - i] / coefficient[k][k] != 0) {
              if (coefficient[k][k] != 0) {
                additional_y.put(n - i, -coefficient[k][n - i] / coefficient[k][k]);
              }
            }
          } else {
            x = x - y * coefficient[k][n - i] / coefficient[k][k];
          }
          i++;
        }
      }

      if (additional_y.size() > 0) {
        StringBuilder temporary_x = new StringBuilder();
        temporary_x.append("(");

        if (x != -0.0) {
          temporary_x.append(x);
          temporary_x.append(" + ");
        }


        int l = 0;

        for (int a: additional_y.keySet()) {
          if (l > 0) {
            temporary_x.append(" + ");
          }

          double coef = additional_y.get(a);

          if (coef == -1.0) {
            if (l > 0) {
              temporary_x.deleteCharAt(temporary_x.length() - 2);
            }
            temporary_x.append("- ");
          } else if (coef < 0) {
            if (l > 0) {
              temporary_x.deleteCharAt(temporary_x.length() - 2);
            }
            temporary_x.append(coef);
          } else if (coef != 1.0){
            temporary_x.append(coef);
          }

          temporary_x.append(map.get(a));
          l++;
        }

        temporary_x.append(")");

        if (temporary_x.toString().matches("\\(q\\d?\\)")) {
          temporary_x.deleteCharAt(temporary_x.length() - 1);
          temporary_x.deleteCharAt(0);
        }
        map.put(k, temporary_x.toString());
        additional_y.clear();
        x_array.add(1000000.0);
      }

      if (Double.isNaN(x)) {
        x_array.add(1000000.0);
        map.put(k, "q" + k);
      } else if (x_array.size() == n - 1 - k) {
        x_array.add(x);
      }

    }

    System.out.println("Ответ:");

    simplify();

    int i = 0;
    for (Double x : x_array) {
      if (x != 1000000.0) {
        System.out.println("x" + (n - i) + " = " + x);
      } else {
        System.out.println("x" + (n - i) + " = " + map.get(n - 1 - i));
      }
      i++;
    }
  }

  private static void simplify() {
    for (String x: map.values()) {
      if (x.matches("\\(")) {

      }
    }
  }

  private static void findDeterminant() {
    double determinant = 1;
    for (int i = 0; i < n; i++) {
      determinant = determinant * coefficient[i][i];
    }
    System.out.println("Определитель матрицы равен: " + determinant);
    System.out.println();
  }

  private static void writeMatrix() {
    StringBuilder result = new StringBuilder();
    n = coefficient.length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        double coef = coefficient[i][j];

        if (coef != 0) {
          if (coef < 0) {
            result.deleteCharAt(result.length() - 2);
            result.append("- ");
          }
          if (abs(coef) != 1) {
            result.append(abs(coef));
          }
          result.append("x");
          result.append(j + 1);
          result.append(" + ");
        }
      }
      result.deleteCharAt(result.length() - 2);
      result.append("= ");
      result.append(mems[i]);
      result.append("\n");
    }

    System.out.println(result.toString());
  }
}
