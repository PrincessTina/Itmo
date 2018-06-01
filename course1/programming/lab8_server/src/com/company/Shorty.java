package com.company;

import java.time.ZonedDateTime;

public class Shorty {
  private String name;
  private int age;
  private double height;
  private String hobby;
  private Status status;
  private int id;
  private ZonedDateTime date;

  public Shorty() {}

  public String toString() {
    return "{name: '" + this.name + "', age: " + this.age + ", height: " + this.height + ", hobby: '" + this.hobby + "'" +
        ", status: " + this.status + ", id: " + this.id + ", date: '" + this.date + "'}%";
  }

}