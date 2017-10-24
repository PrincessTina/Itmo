import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static java.lang.Math.*;

class Method extends Thread{
  static double a;
  static double b;
  static int num;
  static double precision;
  private static boolean isDiscontinuous = false;
  private static boolean isTimeLimit = false;
  private static boolean isMinus = false;

  public void run() {
    checkBounds();
    findPrecisionIntegral();
    resetAll();
  }

  private void findPrecisionIntegral() {
    int n = 10;
    int n2 = 20;
    double integral2 = integration(n2);
    double integral1 = integration(n);
    double searchedIntegral;
    double receivedPrecision;

    while (abs(integral2 - integral1) >= 3*precision) {
      n = n2;
      n2 *= 2;
      integral1 = integration(n);
      integral2 = integration(n2);

      if (n2 >= 5242880) {
        addNewTextElement("Не могу быстро посчитать для заданной точности или \nдиапазона. " +
            "\nБудете ждать? (может занять еще несколько минут)\nНа данный момент число разбиений = " + n2, true);

        try {
          Thread.sleep(14000);
          Platform.runLater(() -> Interface.answerField.setDisable(true));

          if (!Interface.answerBox.getChildren().get(Interface.answerBox.getChildren().size() - 1).getAccessibleText().equals("да")) {
            isTimeLimit = true;
            break;
          }
        } catch (NullPointerException ex) {
          isTimeLimit = true;
          break;
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
        }
      }

      if (isDiscontinuous) {
        break;
      }
    }

    if (isDiscontinuous) {
      addNewTextElement("Функция не является непрерывной на [a;b]", false);
    } else if (isTimeLimit) {
      addNewTextElement("Превышено время исполнения", false);
    } else {
      searchedIntegral = integral1;
      receivedPrecision = abs(integral2 - integral1);

      if (searchedIntegral == 0.0) {
        n2 = 0;
      }

      addNewTextElement("Ответ: " + getRound(searchedIntegral) + "\nЧисло разбиений: " + n2 +
          "\nПолученная погрешность: " + receivedPrecision, false);
    }
  }

  private double integration(int n) {
    double h = abs(b - a) / n;
    double integral = getF(a) + getF(b);

    for (int i = 1; i < n; i++) {
      double x = a + i * h;

      integral += 2 * getF(x);

      if (isDiscontinuous) {
        break;
      }
    }
    integral *= h / 2;

    return integral;
  }

  private double getF(double x) {
    double y = 1000000;
    switch (num) {
      case 0:
        y = 7 / (pow(x, 2) + 1);
        break;
      case 1:
        y = 1 / 12d * pow(x, 4) + 1 / 3d * x - 1 / 60d;
        break;
      case 2:
        if ((x == 2) || (x == -2) || (x == 1) || (x == -1)) {
          isDiscontinuous = true;
        } else {
          y = (pow(x, 2) - x + 2) / (pow(x, 4) - 5 * pow(x, 2) + 4);
        }
        break;
      case 3:
        if (x > 230) {
          isDiscontinuous = true;
        } else {
          y = x / (pow(pow(E, x) + 4, 0.5));
        }
        break;
      case 4:
        if ((x <= 0) || (x == 1)) {
          isDiscontinuous = true;
        } else {
          y = 1 / log(x);
        }
        break;
    }
    return y;
  }

  private double getRound(double mean) {
    mean *= 1 / precision;
    mean = round(mean);
    mean *= precision;

    if (isMinus) {
      mean *= -1;
    }
    return mean;
  }

  private void checkBounds() {
    if (a > b) {
      double m = a;
      a = b;
      b = m;
      isMinus = true;
    }
  }

  private void addNewTextElement(String mean, boolean isRequest) {
    Text text = new Text(mean);
    text.setId("answer");
    text.setFill(Color.valueOf("#2f4f4f"));
    VBox.setMargin(text, new Insets(10, 2, 0, 8));

    if (isRequest) {
      text.setFill(Color.DARKCYAN);
      Platform.runLater(() -> {
        Interface.answerField.setDisable(false);
        Interface.answerBox.getChildren().add(text);
      });
    } else {
      Platform.runLater(() -> Interface.answerBox.getChildren().add(text));
    }
  }

  private void resetAll() {
    isDiscontinuous = false;
    isTimeLimit = false;
    isMinus = false;
  }
}
