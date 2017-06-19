package com.company;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
  static int variantOfLocale = 0;

  public static void main(String args[]) {
    CollectionInterface.createInterface();
  }

  static ResourceBundle getResource() {
    Locale locale = new Locale("ru", "RU");
    switch (variantOfLocale) {
      case 1:
        locale = new Locale("en", "IN");
        break;
      case 2:
        break;
      case 3:
        break;
    }
    return ResourceBundle.getBundle("com.company.Text", locale)
;  }
}