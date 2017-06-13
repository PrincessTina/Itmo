package com.company;

import java.util.Comparator;

class NameComparator implements Comparator<Shorty> {
  public int compare(Shorty a, Shorty b){
    return a.name.compareTo(b.name);
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
    return a.hobby.compareTo(b.hobby);
  }
}

class StatusComparator implements Comparator<Shorty> {
  public int compare(Shorty a, Shorty b){
    return a.status.compareTo(b.status);
  }
}

class IdComparator implements Comparator<Shorty> {
  public int compare(Shorty a, Shorty b){
    return a.id - b.id;
  }
}
