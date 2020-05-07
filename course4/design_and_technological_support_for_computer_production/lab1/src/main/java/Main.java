import static java.lang.Math.*;

import Constants.MainResistorMaterialParams;
import Structure.CRow;
import Structure.RRow;
import Structure.ResistorMaterialParams;
import Structure.Surface;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Main {
  private static final int[] R = {10000, 1300, 1800, 5000, 250};
  private static final int[] deltaR = {10, 20, 20, 10, 10};
  private static final double[] W = {0.003, 0.01, 0.005, 0.01, 0.008};
  private static final int[] C = {800, 350};
  private static final double H = 0.1;

  public static void main(String[] args) {
    int p;
    int resistorMaterialIndex;
    Map<Integer, ArrayList<RRow>> surfaceChangesHistory = new HashMap<>();

    RRow[] tableR = getBasicTableR();
    CRow[] tableC = getBasicTableC();

    p = getOptimalP(tableR);
    resistorMaterialIndex = getResistorMaterialIndex(tableR, p);

    setResistorsFormCoefficient(tableR, p);
    determineResistorsSize(tableR, p, resistorMaterialIndex, surfaceChangesHistory);

    setCapacitorSurface(tableC);

    writeFullData(p, resistorMaterialIndex, tableR, tableC, surfaceChangesHistory);
  }

  /**
   * Находит длину и ширину конденсатора, соответствующие его площади
   * Поиск a осуществляется в диапазоне от 0.1 до S, b - любое
   *
   * @param S - площадь конденсатора
   * @return объект структуры Surface, содержащий найденные длину и ширину конденсатора
   */
  private static Surface getAnySurfaceParams(double S) {
    double a = 0.1;
    double b = S / a;

    for (double i = 0.1; i < S; i += 0.1) {
      double iRounded = gridStepRound(i, RoundMode.round);
      double jRounded = gridStepRound(S / iRounded, RoundMode.round);

      if (abs(iRounded - jRounded) < abs(a - b)) {
        a = iRounded;
        b = jRounded;
      }
    }

    return new Surface(a, b);
  }

  /**
   * Находит длину и ширину конденсатора, соответствующие его площади, в виде десятичной дроби с точностью до 0.1
   * Поиск осуществляется в диапазоне от 0.1 до S
   * Оставляет нулевые значения длины и ширины, если нет подходящих
   *
   * @param S - площадь конденсатора
   * @return объект структуры Surface, содержащий найденные длину и ширину конденсатора
   */
  private static Surface getGridWalkingSurfaceParams(double S) {
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

  /**
   * Исследует, заканчивается ли число на 5
   *
   * @param value - исследуемое число
   * @return true / false
   */
  private static boolean checkIfEndsAtFive(double value) {
    while (round(value) != value) {
      value *= 10;
    }

    return value % 5 == 0;
  }

  /**
   * Рассчитывает и устанавливает длину и ширину для каждого конденсатора
   *
   * @param tableC - таблица конденсаторов
   */
  private static void setCapacitorSurface(CRow[] tableC) {
    for (CRow cRow : tableC) {
      Surface surface;

      cRow.S = 100 * (double) cRow.C / cRow.C0;
      cRow.S = checkIfEndsAtFive(cRow.S) ? cRow.S : limitRound(cRow.S, RoundMode.round, 0.01);

      surface = getGridWalkingSurfaceParams(cRow.S);

      if (surface.a == 0 || surface.b == 0) {
        surface = getAnySurfaceParams(cRow.S);
      }

      cRow.a = surface.a;
      cRow.b = surface.b;
    }
  }

  /**
   * Создает историю изменений для резистора, если еще не была создана
   * Добавляет в историю новое изменение, если уже была создана
   *
   * @param surfaceChangesHistory - история изменений
   * @param i                     - номер резистора в таблице резисторов
   * @param rRow                  - строка таблицы резисторов; параметры резистора
   */
  private static void complementChangesHistory(Map<Integer, ArrayList<RRow>> surfaceChangesHistory, int i, RRow rRow) {
    if (surfaceChangesHistory.containsKey(i)) {
      surfaceChangesHistory.get(i).add(new RRow(rRow.b, rRow.lCalc, rRow.l, rRow.deltaRFact));
    } else {
      ArrayList<RRow> historyTable = new ArrayList<>();
      historyTable.add(new RRow(rRow.b, rRow.lCalc, rRow.l, rRow.deltaRFact));
      surfaceChangesHistory.put(i, historyTable);
    }
  }

  /**
   * Рассчитывает предполагаемую длину резистора и вносит данные в историю изменений площади резистора
   *
   * @param rRow                  - строка таблицы резисторов; параметры резистора
   * @param p                     - оптимальное удельное сопротивление
   * @param i                     - номер резистора в таблице резисторов
   * @param surfaceChangesHistory - история изменений
   */
  private static void countResistorLength(RRow rRow, int p, int i, Map<Integer, ArrayList<RRow>> surfaceChangesHistory) {
    double R;

    rRow.lCalc = limitRound(rRow.k * rRow.b, RoundMode.round, 0.0001);
    rRow.l = gridStepRound(rRow.lCalc, RoundMode.round);
    R = rRow.l * p / rRow.b;
    rRow.deltaRFact = limitRound(abs(rRow.R - R) / rRow.R * 100, RoundMode.round, 0.1);

    complementChangesHistory(surfaceChangesHistory, i, rRow);
  }

  /**
   * Рассчитывает длину и пересчитывает ширину резисторов
   *
   * @param tableR                - таблица резисторов
   * @param p                     - оптимальное удельное сопротивление
   * @param resistorMaterialIndex - индекс материала резистивной пленки
   * @param surfaceChangesHistory - история изменений
   */
  private static void determineResistorsSize(RRow[] tableR, int p, int resistorMaterialIndex, Map<Integer, ArrayList<RRow>> surfaceChangesHistory) {
    int W0 = MainResistorMaterialParams.params[resistorMaterialIndex].W0;

    for (int i = 0; i < tableR.length; i++) {
      RRow rRow = tableR[i];

      if (rRow.k < 10) {
        rRow.bW = gridStepRound(10 * sqrt(p * rRow.W / (rRow.R * W0)), RoundMode.ceil);
        rRow.b = max(rRow.bExact, rRow.bW);

        countResistorLength(rRow, p, i, surfaceChangesHistory);

        while (rRow.deltaRFact > rRow.deltaR) {
          rRow.b += H;
          countResistorLength(rRow, p, i, surfaceChangesHistory);
        }
      } else {
        System.err.println("Меандр"); // TODO: написать логику для меандров, формула: 2,55n + m = k, где m = l/b
      }
    }
  }

  /**
   * Устанавливает коэффициент формы k для каждого резистора
   *
   * @param tableR - таблица резисторов
   * @param p      - оптимальное удельное сопротивление
   */
  private static void setResistorsFormCoefficient(RRow[] tableR, int p) {
    for (RRow rRow : tableR) {
      rRow.k = (double) rRow.R / p;
    }
  }

  /**
   * Выбор материала резистивной пленки
   *
   * @param tableR - таблица резисторов
   * @param p      - оптимальное удельное сопротивление
   * @return индекс материала
   */
  private static int getResistorMaterialIndex(RRow[] tableR, int p) {
    int index = 0;
    int W0 = 0;

    start:
    for (int i = 0; i < MainResistorMaterialParams.params.length; i++) {
      ResistorMaterialParams params = MainResistorMaterialParams.params[i];

      if (p < params.pBottom || p > params.pTop) {
        continue;
      }

      for (RRow rRow : tableR) {
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
   * Рассчитывает и возвращает оптимальное удельное сопротивление
   *
   * @param tableR - таблица резисторов
   * @return оптимальное удельное сопротивление
   */
  private static int getOptimalP(RRow[] tableR) {
    int numerator = 0;
    double denominator = 0;

    for (RRow rRow : tableR) {
      numerator += rRow.R;
      denominator += pow(rRow.R, -1);
    }

    return highestLevelRound(sqrt(numerator / denominator));
  }

  /**
   * Округление до заданного разряда
   *
   * @param value - округляемое значение
   * @param mode  - режим округления: ceil / floor / round
   * @param limit - разряд
   * @return округленное значение
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
   *
   * @param value - округляемое значение
   * @param mode  - режим округления
   * @return округленное значение
   */
  private static double gridStepRound(double value, RoundMode mode) {
    return limitRound(value, mode, H);
  }

  /**
   * Округляет до старшего разряда (первой цифры числа)
   *
   * @param value - округляемое число
   * @return округленное число
   */
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
   * Выводит на консоль параметры материала конденсатора
   */
  private static void writeCapacitorMaterialParams() {
    System.out.println("materialName: Моноокись германия material: Алюминий А99 C0: (5 - 15) * 10^3 B: 10 - 5 e: 11 - 12");
  }

  /**
   * Возвращает строковое представление числа
   * Помогает устранить лишние нули в записи десятичной дроби, полученные в результате некорректных расчетов java
   * по типу 7.0000000000000001 путем сокращения выписываемой дробной части числа до 4 символов
   *
   * @param value      - число
   * @param objectType - тип: double / int
   * @return строковое представление числа
   */
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
   * Выводит на консоль строку из таблицы
   *
   * @param object          - таблица
   * @param format          - формат, в котором будет выписана строка
   * @param ignorableFields - поля, которые необходимо игнорировать
   */
  private static void writeTableRow(Object object, String format, ArrayList<?> ignorableFields) {
    Field[] fields = object.getClass().getDeclaredFields();
    ArrayList<String> values = new ArrayList<>();

    for (Field field : fields) {
      String fieldType = field.getType().toString() + "_t";
      String fieldName = field.getName();
      Object value = new Object();
      ObjectType objectType = ObjectType.default_t;

      if (ignorableFields.contains(fieldName)) {
        continue;
      }

      try {
        if (!field.isAccessible()) {
          field.setAccessible(true);
        }

        value = field.get(object);
        objectType = ObjectType.valueOf(fieldType);
      } catch (IllegalArgumentException | IllegalAccessException ignored) {
      }

      values.add(fieldName + ": " + getFormattedValue(value, objectType));
    }

    System.out.printf(format, values.toArray());
    System.out.println();
  }

  /**
   * Выводит на консоль представление таблицы резисторов, таблицы конденсаторов; параметры материала конденсатора
   *
   * @param tableR                - таблица резисторов
   * @param tableC                - таблица конденсаторов
   * @param surfaceChangesHistory - история изменений площади резисторов
   */
  private static void writeFullTables(RRow[] tableR, CRow[] tableC, Map<Integer, ArrayList<RRow>> surfaceChangesHistory) {
    ArrayList<?> ignorableFields = new ArrayList<>(Arrays.asList("R", "W", "k", "bExact", "bW", "deltaR"));
    StringBuilder tableRFieldFormat = new StringBuilder();
    StringBuilder surfaceChangesHistoryFormat = new StringBuilder();
    StringBuilder tableCFieldFormat = new StringBuilder();

    for (Field field : RRow.class.getDeclaredFields()) {
      tableRFieldFormat.append("%-");
      tableRFieldFormat.append(field.getName().length() + 9); // fieldName [:] [space] [6-digital value] [space]
      tableRFieldFormat.append("s");
    }

    for (Field field : RRow.class.getDeclaredFields()) {
      if (ignorableFields.contains(field.getName())) {
        continue;
      }

      surfaceChangesHistoryFormat.append("%-");
      surfaceChangesHistoryFormat.append(field.getName().length() + 9); // fieldName [:] [space] [6-digital value] [space]
      surfaceChangesHistoryFormat.append("s");
    }

    for (Field field : CRow.class.getDeclaredFields()) {
      tableCFieldFormat.append("%-");
      tableCFieldFormat.append(field.getName().length() + 9); // fieldName [:] [space] [6-digital value] [space]
      tableCFieldFormat.append("s");
    }

    for (RRow rRow : tableR) {
      writeTableRow(rRow, tableRFieldFormat.toString(), new ArrayList<>());
    }
    System.out.println();

    for (int i : surfaceChangesHistory.keySet()) {
      if (surfaceChangesHistory.get(i).size() < 2) {
        continue;
      }

      System.out.printf("R%d:\n", i + 1);

      for (RRow rRow : surfaceChangesHistory.get(i)) {
        writeTableRow(rRow, surfaceChangesHistoryFormat.toString(), ignorableFields);
      }
    }

    System.out.println();
    writeCapacitorMaterialParams();
    System.out.println();

    for (CRow cRow : tableC) {
      writeTableRow(cRow, tableCFieldFormat.toString(), new ArrayList<>());
    }
  }

  /**
   * Выводит на консоль параметры материала резистивной пленки
   *
   * @param params - параметры материала резистивной пленки
   */
  private static void writeResistorMaterialParams(ResistorMaterialParams params) {
    System.out.println("materialName: " + params.materialName + " p: " + params.p + " R: " + params.R + " W0: " + params.W0);
  }

  /**
   * Выводит все результаты на консоль
   *
   * @param p                     - оптимальное удельное сопротивление
   * @param resistorMaterialIndex - индекс материала резистивной пленки
   * @param tableR                - таблица резисторов
   * @param tableC                - таблица конденсаторов
   * @param surfaceChangesHistory - история изменений площади резисторов
   */
  private static void writeFullData(int p, int resistorMaterialIndex, RRow[] tableR, CRow[] tableC, Map<Integer, ArrayList<RRow>> surfaceChangesHistory) {
    System.out.println("p: " + p + "\n");
    writeResistorMaterialParams(MainResistorMaterialParams.params[resistorMaterialIndex]);
    System.out.println();
    writeFullTables(tableR, tableC, surfaceChangesHistory);
  }

  /**
   * Создает, наполняет и возвращает таблицу C (конденсаторов) исходя из начальных данных C
   *
   * @return таблицу C
   */
  private static CRow[] getBasicTableC() {
    int length = C.length;
    CRow[] tableC = new CRow[length];

    for (int i = 0; i < length; i++) {
      tableC[i] = new CRow(C[i]);
    }

    return tableC;
  }

  /**
   * Создает, наполняет и возвращает таблицу R (резисторов) исходя из начальных данных R, deltaR, W
   *
   * @return таблицу R
   */
  private static RRow[] getBasicTableR() {
    int length = R.length;
    RRow[] tableR = new RRow[length];

    for (int i = 0; i < length; i++) {
      tableR[i] = new RRow(R[i], deltaR[i], W[i]);
    }

    return tableR;
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
