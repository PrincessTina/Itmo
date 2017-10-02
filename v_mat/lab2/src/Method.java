import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Scanner;

import static java.lang.Math.*;

class Method {
  static double a;
  static double b;
  static int num;
  static double precision;
  private static boolean isDiscontinuous = false;
  private static boolean isTimeLimit = false;

  static void calculation() {
    checkBounds();
    findPrecisionIntegral();
    resetAll();
  }

  private static void findPrecisionIntegral() {
    int n = 10;
    int n2 = 20;
    double searchedIntegral;
    double receivedPrecision;

    while (abs(integration(n2) - integration(n)) >= abs(precision)) {
      n = n2;
      n2 *= 2;

      if (n2 > 10485760) {
        addNewTextElement("Не могу быстро посчитать для заданной точности или диапазона. \n" +
            "Будете ждать? (может занять еще несколько минут)\nНа данный момент число разбиений = " + n2, true);

        //if (new Scanner(System.in).nextLine().equals("нет")) {
          //isTimeLimit = true;
          //break;
        //}
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
      searchedIntegral = integration(n2);
      receivedPrecision = abs(integration(n2) - integration(n));

      if (searchedIntegral == 0.0) {
        n2 = 0;
      }

      addNewTextElement("Ответ: " + getRound(searchedIntegral) + "\nЧисло разбиений: " + n2 +
          "\nПолученная погрешность: " + receivedPrecision, false);
    }
  }

  private static double integration(int n) {
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

  private static double getF(double x) {
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

  private static double getRound(double mean) {
    mean *= 1 / precision;
    mean = round(mean);
    mean *= precision;

    return mean;
  }

  private static void checkBounds() {
    if (a > b) {
      double m = a;
      a = b;
      b = m;
    }
  }

  private static void addNewTextElement(String mean, boolean isRequest) {
    Text text = new Text(mean);
    VBox.setMargin(text, new Insets(10, 10, 0, 10));

    if (isRequest) {
      text.setFill(Color.DARKCYAN);
    }

    Interface.answerBox.getChildren().add(text);
  }

  private static void resetAll() {
    isDiscontinuous = false;
    isTimeLimit = false;
  }
}
