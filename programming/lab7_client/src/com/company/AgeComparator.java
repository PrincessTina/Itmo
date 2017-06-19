package com.company;

import java.util.Comparator;

class NameComparator implements Comparator<Shorty> {
  public int compare(Shorty a, Shorty b){
    return a.name.compareToIgnoreCase(b.name);
  }
}

class AgeComparator implements Comparator<Shorty> {
  public int compare(Shorty a, Shorty b){
    return a.age - b.age;
  }
}

class HeightComparator implements Comparator<Shorty> {
  public int compare(Shorty a, Shorty b){
    if (a.height - b.height > 0) {
      return 1;
    } else if (a.height - b.height < 0) {
      return -1;
    } else {
      return 0;
    }
  }
}

class HobbyComparator implements Comparator<Shorty> {
  public int compare(Shorty a, Shorty b){
    return a.hobby.compareToIgnoreCase(b.hobby);
  }
}

class StatusComparator implements Comparator<Shorty> {
  public int compare(Shorty a, Shorty b){
    return switchStatus(a.status).compareTo(switchStatus(b.status));
  }

  private static String switchStatus(Status status) {
    String meaning;
    switch (status) {
      case married:
        meaning = "married";
        break;
      case have_a_girlfriend:
        meaning = "have_a_girlfriend";
        break;
      case idle:
        meaning = "idle";
        break;
      case single:
        meaning = "single";
        break;
      default:
        meaning = "all_is_complicated";
    }
    return meaning;
  }
}

class IdComparator implements Comparator<Shorty> {
  public int compare(Shorty a, Shorty b){
    return a.id - b.id;
  }
}
