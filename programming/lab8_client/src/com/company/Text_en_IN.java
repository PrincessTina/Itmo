package com.company;

import java.util.ListResourceBundle;

public class Text_en_IN extends ListResourceBundle {
  private final Object[][] content = {
      {"file", "Файл"},
      {"load", "Загрузить"},
      {"mode", "Режим"},
      {"daylight", "Светлый"},
      {"nighttime", "Темный"},
      {"citySpirit", "С картинкой"},
      {"language", "Язык"},
      {"russian", "Русский"},
      {"german", "Немецкий"},
      {"english", "Английский"},
      {"bulgarian", "Болгарский"},
      {"exit", "Выход"},
      {"commands", "Команда"},
      {"info", "Информация"},
      {"add", "Добавить"},
      {"remove_first", "Удалить первый"},
      {"remove_all", "Удалить такие, как"},
      {"modify", "Изменить"},
      {"help", "Помощь"},
      {"platoon", "Отряд-менеджер"},
      {"help_text", "Use one of the commands: \n" +
          "\n Info: Displays information about the collection" +
          "\n Add: Adds new element in json or other format" +
          "\n Remove first: Removes the first element of the collection" +
          "\n Remove All: Removes all elements that match the specified in json format " +
          "\n Modify: Changes the element with given index " +
          "\n Load: Update" +
          "\n Mode: Sets one of the mods" +
          "\n\n*Also you can click the right mouse button or on one of the " +
          "table columns to choose variant of sorting elements in the collection"}
  };

  public Object[][] getContents() {
    return content;
  }
}
