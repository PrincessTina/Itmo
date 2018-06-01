package com.company;

import java.util.ListResourceBundle;

public class Text_en_IN extends ListResourceBundle {
  private final Object[][] content = {
      {"file", "File"},
      {"load", "Load"},
      {"mode", "Mode"},
      {"daylight", "DayLight"},
      {"nighttime", "NightTime"},
      {"citySpirit", "One more"},
      {"language", "Language"},
      {"russian", "Russian"},
      {"german", "German"},
      {"english", "English"},
      {"bulgarian", "Bulgarian"},
      {"exit", "Exit"},
      {"commands", "Command"},
      {"info", "Info"},
      {"add", "Add"},
      {"remove_first", "Remove first"},
      {"remove_all", "Remove all as"},
      {"modify", "Modify"},
      {"help", "Help"},
      {"platoon", "Platoon manager"},
      {"help_text", "Use one of the commands: \n" +
          "\n Info: Displays information about the collection" +
          "\n Add: Adds new element in json or other format" +
          "\n Remove first: Removes the first element of the collection" +
          "\n Remove All: Removes all elements that match the specified in json \n format " +
          "\n Modify: Changes the element with given index " +
          "\n Load: Update" +
          "\n Mode: Sets one of the mods" +
          "\n\n*Also you can click the right mouse button or on one of the " +
          "table columns to choose variant of sorting elements in the collection"},
      {"sort_by", "Sort by"},
      {"names", "name"},
      {"ages", "age"},
      {"heights", "height"},
      {"hobbies", "hobby"},
      {"statuses", "status"},
      {"ides", "id"},
      {"dates", "date"},
      {"check_connection", "Check your connection with server"},
      {"empty_collection", "Collection's empty"},
      {"successfully_deleted", "Successfully deleted"},
      {"warning", "Warning"},
      {"name", "Name"},
      {"age", "Age"},
      {"height", "Height"},
      {"hobby", "Hobby"},
      {"status", "Status"},
      {"id", "Id"},
      {"date", "Creation date"},
      {"remove_elements", "Remove elements"},
      {"input", "Input objects in json format"},
      {"objects", "object(s)"},
      {"check_correctness", "Check correctness of entry"},
      {"index", "Index"},
      {"input_index", "Input index of object that you'd like to \nchange and then it's new parameters"},
      {"input_index_", "Input index"},
      {"index_out", "Index out of range"},
      {"age_can", "Age can't be negative"},
      {"height_can", "Height can only be positive"},
      {"successfully_modified", "Successfully modified"},
      {"error", "Error"},
      {"can_null", "Complete empty fields"},
      {"loading", "Data're outdated.\nLoading new data.."},
      {"add_element", "Add element"},
      {"values", "Values"},
      {"successfully_added", "Successfully added"}
  };

  public Object[][] getContents() {
    return content;
  }
}
