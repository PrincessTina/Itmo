package com.company;

import java.util.*;

public class Shorty implements Comparator<Shorty> {
  private String name;
  private int age;
  private double height;
  private String hobby;
  private Status status;

  Shorty(String name, int age, double height, String hobby, Status status) {
    this.name = name;
    this.age = age;
    this.height = height;
    this.hobby = hobby;
    this.status = status;
  }

  // Is used for shorty comparator
  Shorty() {}

  public String toString() {
    return "name: " + this.name + ", age: " + this.age + ", height: " + this.height + ", hobby: " + this.hobby + ", status: " + this.status;
  }

  public int compare(Shorty a, Shorty b) {
    if (a.name.compareTo(b.name) == 0) {
      if (a.age == b.age) {
        if (a.height == b.height) {
          if (a.hobby.compareTo(b.hobby) == 0) {
            return a.status.toString().compareTo(b.status.toString());
          } else return a.hobby.compareTo(b.hobby);
        } else if (a.height - b.height > 0) {
          return 1;
        } else return -1;
      } else return a.age - b.age;
    } else return a.name.compareTo(b.name);
  }

  public static ArrayList<Shorty> defaultCollection() {
    ArrayList<Shorty> defaultCollection = new ArrayList<>();

    defaultCollection.add(new Shorty("Neznayka", 11, 143.1, "bright colors", Status.idle));
    defaultCollection.add(new Shorty("Pilulkin", 14, 157.8, "doctor", Status.have_a_girlfriend));
    defaultCollection.add(new Shorty("Shpuntik", 10, 139.2, "main tech", Status.have_a_girlfriend));
    defaultCollection.add(new Shorty("Shpuntik", 10, 139.2, "main tech", Status.have_a_girlfriend));

    return defaultCollection;
  }
}