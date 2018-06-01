package com.company;

import java.util.ListResourceBundle;

public class Text_de_DE extends ListResourceBundle {
  private final Object[][] content = {
      {"file", "Datei"},
      {"load", "Herunterladen"},
      {"mode", "Modus"},
      {"daylight", "Helle"},
      {"nighttime", "Dunkel"},
      {"citySpirit", "Mit dem Bild"},
      {"language", "Sprache"},
      {"russian", "Russisch"},
      {"german", "Deutsch"},
      {"english", "Englisch"},
      {"bulgarian", "Bulgarisch"},
      {"exit", "Ausgang"},
      {"commands", "Befehl"},
      {"info", "Informationen"},
      {"add", "Hinzufügen"},
      {"remove_first", "Entfernen Sie den ersten"},
      {"remove_all", "Entfernen wie"},
      {"modify", "Ändern"},
      {"help", "Hilfe"},
      {"platoon", "Kader-Manager"},
      {"help_text", "Verwenden Sie einen der Befehle: \n" +
          "\n Info: Zeigt Informationen über die Sammlung" +
          "\n Hinzufügen: Fügt ein neues Element in der Auflistung im Format \n json" +
          "\n Löschen erste: Entfernt das erste Element der Auflistung" +
          "\n Löschen Sie wie folgt: Entfernen Sie alle Elemente, die identisch sind angegebenen Format json" +
          "\n Ändern: Ändert die Elemente mit der angegebenen Nummer" +
          "\n Neu laden: Aktualisieren/laden" +
          "\n Modus: Legt einen der Modi" +
          "\n\n*Sie können auch mit der rechten Maustaste oder klicken Sie auf einen der" +
          " Spalten der Tabelle, um die Option Sortieren"},
      {"sort_by", "Sortieren nach"},
      {"names", "Namen"},
      {"ages", "Alter"},
      {"heights", "Wachstum"},
      {"hobbies", "Steckenpferd"},
      {"statuses", "Zustand"},
      {"ides", "Iden"},
      {"dates", "Erstellungsdatum"},
      {"check_connection", "Überprüfen Sie Ihre Verbindung mit dem Server"},
      {"empty_collection", "Sammlung ist leer"},
      {"successfully_deleted", "Erfolgreich entfernt"},
      {"warning", "Warnung"},
      {"name", "Name"},
      {"age", "Alter"},
      {"height", "Wachstum"},
      {"hobby", "Steckenpferd"},
      {"status", "Zustand"},
      {"id", "Iden"},
      {"date", "Erstellungsdatum"},
      {"remove_elements", "Entfernen von Elementen"},
      {"input", "Geben Sie das Objekt im Format json"},
      {"objects", "Objekte"},
      {"check_correctness", "Überprüfen Sie die korrekte Eingabe"},
      {"index", "Index"},
      {"input_index", "Geben Sie den index des Objekts, das Sie wollen \nändern und dann die neuen Einstellungen"},
      {"input_index_", "Geben Sie den index des Objekts"},
      {"index_out", "Der index des Objekts außerhalb des verfügbaren Bereichs"},
      {"age_can", "Das Alter kann nicht negativ sein"},
      {"height_can", "Das Wachstum kann nur positiv sein"},
      {"successfully_modified", "Erfolgreich geändert wurde"},
      {"error", "Fehler"},
      {"can_null", "Füllen Sie die leeren Felder"},
      {"loading", "Die Daten sind veraltet.\nLade neue Daten.."},
      {"add_element", "Element hinzufügen"},
      {"values", "Werte"},
      {"successfully_added", "Erfolgreich Hinzugefügt"}
  };

  public Object[][] getContents() {
    return content;
  }
}
