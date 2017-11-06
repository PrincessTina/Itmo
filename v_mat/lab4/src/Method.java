import jdk.nashorn.internal.runtime.ECMAException;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

class Method {
  static double x0;
  static double y0;
  static double last;
  static double precision;
  static int number;
  private static ArrayList<Double> point_x = new ArrayList<>();
  private static ArrayList<Double> point_y = new ArrayList<>();

  static void calculation() throws Exception {
    double h = 0.5;

    while (abs(function(h) - function(h / 2)) > precision) {
      h /= 2;
    }

    Interface.point_x = point_x;
    Interface.point_y = point_y;
  }

  private static double function(double h) throws Exception {
    double y1 = y0, y2 = y0;
    double x = x0;
    double k0, k1, k2, k3;

    point_x.clear();
    point_y.clear();

    while (x != last + h) {
      y1 = y2;

      point_x.add(x);
      point_y.add(y1);

      k0 = h * f(x, y1);
      k1 = h * f(x + h/2, y1 + k0 / 2);
      k2 = h * f(x + h/2, y1 + k1 / 2);
      k3 = h * f(x + h, y1 + k2);

      y2 = y1 + (k0 + 2 * k1 + 2 * k2 + k3) / 6;
      x += h;

      if (Double.isNaN(y2)) {
        throw new Exception("NaN");
      }
    }

    return y1;
  }

  private static double f(double x, double y) {
    switch (number) {
      case 0:
        return 12*x - 3*pow(x, 2) + 8*pow(x, 3);
      case 1:
        return -5*y + 8;
      case 2:
        return pow(x, 2) - 2*y;
      case 3:
        return pow(x, 3) + 3*pow(x, 2) - 10*x + 2*y - 0.04*pow(y, 3);
      default:
        return 10;
    }
  }
}
