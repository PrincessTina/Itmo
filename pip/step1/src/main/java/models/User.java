package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(name = "user_id")
  private int user_id;

  @Column(name = "name")
  private String name;

  @Column(name = "date_of_check")
  private String date_of_check;

  public int getId() {
    return user_id;
  }

  public void setId(int id) {
    this.user_id = user_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDateOfCheck() {
    return date_of_check;
  }

  public void setDateOfCheck(String village) {
    this.date_of_check = village;
  }
}