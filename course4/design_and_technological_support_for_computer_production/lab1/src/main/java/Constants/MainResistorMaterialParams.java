package Constants;

import Structure.ResistorMaterialParams;

public class MainResistorMaterialParams {
  public static ResistorMaterialParams[] params;

  static {
    params = new ResistorMaterialParams[] {
      new ResistorMaterialParams("Сплав РС - 3001", 800, 3000, 50,30000,2),
      new ResistorMaterialParams("Сплав РС - 3710", 100, 2000, 10,20000,2),
        new ResistorMaterialParams("Кермет К-50С", 1000, 10000, 100,100000,2),
        new ResistorMaterialParams("Специальный сплав №3", 350, 500, 100,50000,2),
        new ResistorMaterialParams("Тантал ТВЧ", 10, 100, 1,1000,3),
        new ResistorMaterialParams("Нихром", 50, 300, 5,3000,1),
        new ResistorMaterialParams("Хром", 500, 500, 50,30000,1)
    };
  }
}
