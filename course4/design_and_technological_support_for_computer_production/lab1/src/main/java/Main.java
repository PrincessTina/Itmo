import static java.lang.Math.*;

import Constants.MainResistorMaterialParams;
import Structure.CTable;
import Structure.RTable;
import Structure.Surface;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

public class Main {
  private static int[] R = {200, 2000, 8500, 15000, 950};
  private static int[] deltaR = {10, 20, 20, 10, 10};
  private static double[] W = {0.04, 0.005, 0.008, 0.001, 0.01};
  private static int[] C = {1500, 4000};
  private static double H = 0.1;

  public static void main(String[] args) {
    int p;
    int resistorMaterialIndex;

    RTable[] rTable = getDefaultRTable();
    CTable[] cTable = getDefaultCTable();

    p = countOptimalP(rTable);
    resistorMaterialIndex = getResistorMaterialIndex(rTable, p);

    setResistorFormCoefficient(rTable, p);
    determineResistorSize(rTable, p, resistorMaterialIndex);

    countCapacitorSurface(cTable);

    try {
      writeFullTables(rTable, cTable);
    } catch (IllegalAccessException ignored) {}

    //writeFormatTest();
  }

  private static Surface findGridWalkingSurfaceParams(double S) {
    double a = 0;
    double b = 0;

    for (double i = 0.1; i < S; i += 0.1) {
      for (double j = S; j >= i; j -= 0.1) {
        double iRounded = gridStepRound(i, RoundMode.round);
        double jRounded = gridStepRound(j, RoundMode.round);

        if (iRounded * jRounded == S) {
          if ((a == 0 && b == 0) || (abs(iRounded - jRounded) < abs(a - b))) {
            a = iRounded;
            b = jRounded;
          }
        }
      }
    }

    return new Surface(a, b);
  }

  private static Surface findAnySurfaceParams(double S) {
    double a = 0.1;
    double b = S / a;

    for (double i = 0.1; i < S; i += 0.1) {
      double iRounded = gridStepRound(i, RoundMode.round);
      double jRounded = gridStepRound(S / a, RoundMode.round);

      if (abs(iRounded - jRounded) < abs(a - b)) {
        a = iRounded;
        b = jRounded;
      }
    }

    return new Surface(a, b);
  }

  private static boolean checkIfEndsAtFive(double value) {
    while (round(value) != value) {
      value *= 10;
    }

    return value % 5 == 0;
  }

  private static void countCapacitorSurface(CTable[] cTable) {
    for (CTable aCTable : cTable) {
      double S;
      Surface surface;

      aCTable.S = (double) aCTable.C / aCTable.C0;
      aCTable.S = checkIfEndsAtFive(aCTable.S) ? aCTable.S : limitRound(aCTable.S, RoundMode.round, 0.01);
      S = aCTable.S * 100;

      surface = findGridWalkingSurfaceParams(S);

      if (surface.a == 0 || surface.b == 0) {
        surface = findAnySurfaceParams(S);
      }

      aCTable.a = surface.a;
      aCTable.b = surface.b;
    }
  }

  private static void countResistorLength(RTable aRTable, int p) {
    double R;

    aRTable.lCalc = limitRound(aRTable.k * aRTable.b, RoundMode.round, 0.0001);
    aRTable.l = gridStepRound(aRTable.lCalc, RoundMode.round);

    R = aRTable.l * p / aRTable.b;
    aRTable.deltaRFact = limitRound(abs(aRTable.R - R) / aRTable.R * 100, RoundMode.round, 0.1);
  }

  private static void determineResistorSize(RTable[] rTable, int p, int resistorMaterialIndex) {
    int W0 = MainResistorMaterialParams.params[resistorMaterialIndex].W0;

    for (RTable aRTable : rTable) {
      if (aRTable.k < 10) {
        aRTable.bW = gridStepRound(10 * sqrt(p * aRTable.W / (aRTable.R * W0)), RoundMode.ceil);
        aRTable.b = max(aRTable.bExact, aRTable.bW);

        countResistorLength(aRTable, p);

        while (aRTable.deltaRFact > aRTable.deltaR) {
          aRTable.b += H;
          countResistorLength(aRTable, p);
        }
      } else {
        System.err.println("Меандр");
      }
    }
  }

  private static void setResistorFormCoefficient(RTable[] rTable, int p) {
    for (RTable aRTable : rTable) {
      aRTable.k = (double) aRTable.R / p;
    }
  }

  /**
   * Выбирается материал резистивной пленки
   */
  private static int getResistorMaterialIndex(RTable[] rTable, int p) {
    start:
    for (int i = 0; i < MainResistorMaterialParams.params.length; i++) {
      if (p < MainResistorMaterialParams.params[i].pBottom || p > MainResistorMaterialParams.params[i].pTop) {
        continue;
      }

      for (RTable aRTable : rTable) {
        if (aRTable.R < MainResistorMaterialParams.params[i].RBottom || aRTable.R > MainResistorMaterialParams.params[i].RTop) {
          continue start;
        }
      }

      return i;
    }

    return 0;
  }

  /**
   * Округление до заданного разряда
   */
  private static double limitRound(double value, RoundMode mode, double limit) {
    switch (mode) {
      case ceil:
        return ceil(value / limit) * limit;
      case floor:
        return floor(value / limit) * limit;
      case round:
      default:
        return round(value / limit) * limit;
    }
  }

  /**
   * Округление кратно шагу координатной сетки H, H = 0.1
   */
  private static double gridStepRound(double value, RoundMode mode) {
    return limitRound(value, mode, H);
  }

  /**
   * Округление до старшего разряда (первой цифры числа)
   */
  //TODO: Поменять алгоритм округления (до 3 разряда)
  private static int highestLevelRound(double value) {
    int tenDegree = 1;

    while (floor(value) > 0) {
      value /= 10;
      tenDegree *= 10;
    }

    value *= tenDegree;
    tenDegree /= 10;

    return (int) round(value / tenDegree) * tenDegree;
  }

  /**
   * Подсчет оптимального удельного сопротивления
   */
  private static int countOptimalP(RTable[] rTable) {
    int numerator = 0;
    double denominator = 0;

    for (RTable aRTable : rTable) {
      numerator += aRTable.R;
      denominator += pow(aRTable.R, -1);
    }

    return highestLevelRound(sqrt(numerator / denominator));
  }

  private static String writeFormatTest(Object value, String objectType) {
    System.out.println(new DecimalFormat("###.00").format(value));

    switch (objectType) {
      case "int":
        return value.toString();
      case "double":
      default:
        return "";
    }
  }

  /**
   * Находит все доступные поля объекта
   * Выписывает их имена и значения в формате name: value
   */
  private static void writeFullFields(Object object) throws IllegalAccessException {
    Field[] fields = object.getClass().getDeclaredFields();

    for (Field field : fields) {
      String fieldType = field.getType().toString();
      String fieldName = field.getName();

      Object value = field.get(object);
      System.out.print(fieldName + ": " + value + " ");
    }

    System.out.println();
  }

  private static void writeFullTables(RTable[] rTable, CTable[] cTable) throws IllegalAccessException {
    for (RTable aRTable : rTable) {
      writeFullFields(aRTable);
    }

    System.out.println();

    for (CTable aCTable : cTable) {
      writeFullFields(aCTable);
    }
  }

  private static CTable[] getDefaultCTable() {
    int length = C.length;
    CTable[] cTable = new CTable[length];

    for (int i = 0; i < length; i++) {
      cTable[i] = new CTable(C[i]);
    }

    return cTable;
  }

  private static RTable[] getDefaultRTable() {
    int length = R.length;
    RTable[] rTable = new RTable[length];

    for (int i = 0; i < length; i++) {
      rTable[i] = new RTable(R[i], deltaR[i], W[i]);
    }

    return rTable;
  }
}

enum RoundMode {
  ceil,
  floor,
  round
}
