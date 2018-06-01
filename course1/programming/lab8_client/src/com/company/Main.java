package com.company;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
  static int variantOfLocale;
  static Locale locale;

  public static void main(String args[]) {
    if (args.length == 0) {
      variantOfLocale = 0;
    } else {
      variantOfLocale = Integer.parseInt(args[0]);
    }
    CollectionInterface.createInterface();
  }

  static ResourceBundle getResource() {
    locale = new Locale("ru", "RU");
    switch (variantOfLocale) {
      case 1:
        locale = new Locale("en", "IN");
        break;
      case 2:
        locale = new Locale("de", "DE");
        break;
      case 3:
        locale = new Locale("bg", "BG");
        break;
    }
    return ResourceBundle.getBundle("com.company.Text", locale)
;  }
}