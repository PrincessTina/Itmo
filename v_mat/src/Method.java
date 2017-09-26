
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.*;
import static jdk.nashorn.internal.objects.Global.Infinity;


class Method {
  static double[][] coefficient;
  static double[] mems;
  private static int n = 0;
  private static HashMap<Integer, String> map = new HashMap<>();
  private static HashMap<Integer, Double> map2 = new HashMap<>();
  private static ArrayList<Double> x_array = new ArrayList<>();
  private static boolean isInfinity = false;

  static void calculation() {
    System.out.println("Исходная матрица:");
    writeMatrix();

    directRate();

    reverseRate();

    fullReset();
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
              } else {
                //x_array.set(x_array.indexOf(y), 0.0);
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

        int l = 0;
        double sum = 0;

        if (x != -0.0) {
          temporary_x.append(x);
          l = 1;
          sum += x;
        }

        for (int a : additional_y.keySet()) {
          if (l > 0) {
            temporary_x.append(" + ");
          }

          double coef = additional_y.get(a);

          sum += coef * map2.get(a);

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
          } else if (coef != 1.0) {
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
        map2.put(k, sum);

        additional_y.clear();
        x_array.add(1000000.0);
      }

      if (x == Infinity) {
        addTextElement("Решений нет", true);
        isInfinity = true;
        break;
      }

      if (Double.isNaN(x)) {
        x_array.add(1000000.0);
        map.put(k, "q" + k);
        map2.put(k, 1.0);
      } else if (x_array.size() == n - 1 - k) {
        x_array.add(x);
      }
    }

    if (!isInfinity) {
      simplifyAnswer();
    }
  }

  private static void findResidual() {
    StringBuilder answer = new StringBuilder();
    answer.append("Столбец невязок: \n");
    double b;
    double x;
    double coef;
    double delta;

    for (int k = 0; k < n; k++) {
      b = 0;

      for (int i = 0; i < n; i++) {
        x = x_array.get(n - 1 - i);
        coef = coefficient[k][i];

        if (x == 1000000.0) {
          b += coef * map2.get(i);
        } else {
          b += coef * x;
        }
      }

      delta = abs(mems[k] - b);

      if (delta < 1.0) {
        answer.append("d");
        answer.append(k + 1);
        answer.append(" = ");
        answer.append(delta);
        answer.append("\n");
      } else {
        answer.delete(0, answer.length() - 1);
        answer.append("Решений нет");
        Interface.answerBox.getChildren().remove(Interface.answerBox.getChildren().size() - 1);
        break;
      }
    }
    addTextElement(answer.toString(), true);
    System.out.println(answer);
  }

  private static void simplifyAnswer() {
    int i = 0;
    StringBuilder answer = new StringBuilder();
    answer.append("Столбец неизвестных: \n");

    for (Double x : x_array) {
      answer.append("x");
      answer.append(n - i);
      answer.append(" = ");

      if (x != 1000000.0) {
        answer.append(x);
      } else {
        String x_string = map.get(n - 1 - i);
        if (x_string.matches("\\(.*\\)")) {
          x_string = x_string.substring(1, x_string.length() - 1);
        }

        answer.append(x_string);
      }
      answer.append("\n");
      i++;
    }

    addTextElement(answer.toString(), true);
    System.out.println(answer);

    try {
      Interface.allocation();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

    findResidual();
  }

  private static void findDeterminant() {
    StringBuilder answer = new StringBuilder();
    double determinant = 1;
    for (int i = 0; i < n; i++) {
      determinant = determinant * coefficient[i][i];
    }

    answer.append("Определитель матрицы равен: ");
    answer.append(determinant);

    addTextElement(answer.toString(), true);
    System.out.println(answer.toString());

    getTriangular();
  }

  private static void writeMatrix() {
    double coef;
    double areAllCoefNull;
    StringBuilder result = new StringBuilder();
    n = coefficient.length;

    for (int i = 0; i < n; i++) {
      areAllCoefNull = 0;
      for (int j = 0; j < n; j++) {
        coef = coefficient[i][j];

        if (coef != 0) {
          if (coef < 0) {
            if (result.length() > 1) {
              result.deleteCharAt(result.length() - 2);
            }
            result.append("-");
          }
          if (abs(coef) != 1) {
            result.append(abs(coef));
          }
          result.append("x");
          result.append(j + 1);
          result.append(" + ");
        } else {
          areAllCoefNull += 1;
        }
      }

      if (result.length() > 1) {
        result.deleteCharAt(result.length() - 2);
      }

      if (areAllCoefNull / n == 1.0) {
        result.append("0.0 ");
      }
      result.append("= ");
      result.append(mems[i]);
      result.append("\t\n");
    }


    addTextElement(result.toString(), false);
    System.out.println(result.toString());
  }

  private static void getTriangular() {
    StringBuilder matrix = new StringBuilder();
    matrix.append("Треугольная матрица: \n");

    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        matrix.append(coefficient[k][i]);
        matrix.append(" ");
      }
      matrix.append("\n");
    }

    addTextElement(matrix.toString(), true);
    System.out.println(matrix.toString());
  }

  private static void addTextElement(String mean, boolean isAnswer) {
    Text text = new Text(mean);
    VBox.setMargin(text, new Insets(10, 10, 0, 10));

    if (isAnswer) {
      Interface.answerBox.getChildren().add(text);
    } else {
      Interface.solveBox.getChildren().add(text);
    }
  }

  private static void fullReset() {
    map.clear();
    map2.clear();
    x_array.clear();
    isInfinity = false;

  }
}
