import static java.lang.Math.*;

import Constants.MainResistorMaterialParams;
import Structure.CTable;
import Structure.RTable;
import Structure.ResistorMaterialParams;
import Structure.Surface;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Main {
  private static int[] R = {200, 2000, 8500, 15000, 950};
  private static int[] deltaR = {10, 20, 20, 10, 10};
  private static double[] W = {0.04, 0.005, 0.008, 0.001, 0.01};
  private static int[] C = {1500, 4000};
  private static double H = 0.1;

  public static void main(String[] args) {
    int p;
    int resistorMaterialIndex;

    RTable[] rRows = getDefaultRTable();
    CTable[] cRows = getDefaultCTable();

    p = countOptimalP(rRows);
    resistorMaterialIndex = getResistorMaterialIndex(rRows, p);

    setResistorFormCoefficient(rRows, p);
    determineResistorSize(rRows, p, resistorMaterialIndex);

    countCapacitorSurface(cRows);

    writeFullData(p, resistorMaterialIndex, rRows, cRows);
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

  private static void countCapacitorSurface(CTable[] cRows) {
    for (CTable cRow : cRows) {
      double S;
      Surface surface;

      cRow.S = (double) cRow.C / cRow.C0;
      cRow.S = checkIfEndsAtFive(cRow.S) ? cRow.S : limitRound(cRow.S, RoundMode.round, 0.01);
      S = cRow.S * 100;

      surface = findGridWalkingSurfaceParams(S);

      if (surface.a == 0 || surface.b == 0) {
        surface = findAnySurfaceParams(S);
      }

      cRow.a = surface.a;
      cRow.b = surface.b;
    }
  }

  private static void countResistorLength(RTable rRow, int p) {
    double R;

    rRow.lCalc = limitRound(rRow.k * rRow.b, RoundMode.round, 0.0001);
    rRow.l = gridStepRound(rRow.lCalc, RoundMode.round);

    R = rRow.l * p / rRow.b;
    rRow.deltaRFact = limitRound(abs(rRow.R - R) / rRow.R * 100, RoundMode.round, 0.1);
  }

  private static void complementChangesHistory(Map<String, RTable[]> changesHistory, int i, RTable rRow) {
    //changesHistory.put
  }

  private static void determineResistorSize(RTable[] rRows, int p, int resistorMaterialIndex) {
    int W0 = MainResistorMaterialParams.params[resistorMaterialIndex].W0;
    Map<String, RTable[]> changesHistory = new HashMap<>();

    for (int i = 0; i < rRows.length; i++) {
      RTable rRow = rRows[i];

      if (rRow.k < 10) {
        rRow.bW = gridStepRound(10 * sqrt(p * rRow.W / (rRow.R * W0)), RoundMode.ceil);
        rRow.b = max(rRow.bExact, rRow.bW);

        countResistorLength(rRow, p);

        while (rRow.deltaRFact > rRow.deltaR) {

          rRow.b += H;
          countResistorLength(rRow, p);
        }
      } else {
        System.err.println("Меандр");
      }
    }
  }

  private static void setResistorFormCoefficient(RTable[] rRows, int p) {
    for (RTable rRow : rRows) {
      rRow.k = (double) rRow.R / p;
    }
  }

  /**
   * Выбирается материал резистивной пленки
   */
  private static int getResistorMaterialIndex(RTable[] rRows, int p) {
    int index = 0;
    int W0 = 0;

    start:
    for (int i = 0; i < MainResistorMaterialParams.params.length; i++) {
      ResistorMaterialParams params = MainResistorMaterialParams.params[i];

      if (p < params.pBottom || p > params.pTop) {
        continue;
      }

      for (RTable rRow : rRows) {
        if (rRow.R < params.RBottom || rRow.R > params.RTop) {
          continue start;
        }
      }

      if (params.W0 > W0) {
        W0 = params.W0;
        index = i;
      }
    }

    return index;
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
  private static int countOptimalP(RTable[] rRows) {
    int numerator = 0;
    double denominator = 0;

    for (RTable rRow : rRows) {
      numerator += rRow.R;
      denominator += pow(rRow.R, -1);
    }

    return highestLevelRound(sqrt(numerator / denominator));
  }

  private static void writeFullData(int p, int resistorMaterialIndex, RTable[] rRows, CTable[] cRows) {
    System.out.println("p: " + p + "\n");
    writeResistorMaterialParams(MainResistorMaterialParams.params[resistorMaterialIndex]);
    System.out.println();
    writeFullTables(rRows, cRows);
  }

  private static void writeResistorMaterialParams(ResistorMaterialParams params) {
    System.out.println("materialName: " + params.materialName + " p: " + params.p + " R: " + params.R + " W0: " + params.W0);
  }

  private static void writeFullTables(RTable[] rRows, CTable[] cRows) {
    StringBuilder rRowsFieldFormat = new StringBuilder();
    StringBuilder cRowsFieldFormat = new StringBuilder();

    for (Field field : RTable.class.getDeclaredFields()) {
      rRowsFieldFormat.append("%-");
      rRowsFieldFormat.append(field.getName().length() + 9); // fieldName[:] [space] [6-digital value] [space]
      rRowsFieldFormat.append("s");
    }

    for (Field field : CTable.class.getDeclaredFields()) {
      cRowsFieldFormat.append("%-");
      cRowsFieldFormat.append(field.getName().length() + 9); // fieldName[:] [space] [6-digital value] [space]
      cRowsFieldFormat.append("s");
    }

    for (RTable rRow : rRows) {
      writeFullFields(rRow, rRowsFieldFormat.toString());
    }

    System.out.println();
    writeCapacitorMaterialParams();
    System.out.println();

    for (CTable cRow : cRows) {
      writeFullFields(cRow, cRowsFieldFormat.toString());
    }
  }

  private static void test() {
    Map<String, RTable[]> states = new HashMap<>();
    RTable[] rRows = {new RTable(1, 2, 4.0)};
    states.put("1", rRows);
  }

  private static void writeCapacitorMaterialParams() {
    System.out.println("materialName: Моноокись германия material: Алюминий А99 C0: (5 - 15) * 10^3 B: 10 - 5 e: 11 - 12");
  }

  private static String getFormattedValue(Object value, ObjectType objectType) {
    switch (objectType) {
      case double_t:
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        formatSymbols.setDecimalSeparator('.');
        return new DecimalFormat("###.####", formatSymbols).format(value);
      case int_t:
      default:
        return value.toString();
    }
  }

  /**
   * Находит все доступные поля объекта
   * Выписывает их имена и значения в формате name: value
   */
  private static void writeFullFields(Object object, String format) {
    Field[] fields = object.getClass().getDeclaredFields();
    String[] values = new String[fields.length];

    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      String fieldType = field.getType().toString() + "_t";
      String fieldName = field.getName();
      Object value = new Object();
      ObjectType objectType = ObjectType.default_t;

      try {
        if (!field.isAccessible()) {
          field.setAccessible(true);
        }

        value = field.get(object);
        objectType = ObjectType.valueOf(fieldType);
      } catch (IllegalArgumentException | IllegalAccessException ignored) {}

      values[i] = fieldName + ": " + getFormattedValue(value, objectType);
    }

    System.out.printf(format, values);
    System.out.println();
  }

  private static CTable[] getDefaultCTable() {
    int length = C.length;
    CTable[] cRows = new CTable[length];

    for (int i = 0; i < length; i++) {
      cRows[i] = new CTable(C[i]);
    }

    return cRows;
  }

  private static RTable[] getDefaultRTable() {
    int length = R.length;
    RTable[] rRows = new RTable[length];

    for (int i = 0; i < length; i++) {
      rRows[i] = new RTable(R[i], deltaR[i], W[i]);
    }

    return rRows;
  }
}

enum RoundMode {
  ceil,
  floor,
  round
}

enum ObjectType {
  int_t,
  double_t,
  default_t
}
