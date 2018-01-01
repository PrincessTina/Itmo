package entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;

@Entity
public class Users {
  @Id
  @GeneratedValue
  private int id;
  private String login;
  private String password;
  private Date date_of_check;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_award",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "award_id")
  )
  private ArrayList<Award> awards;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_legend",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "legend_id")
  )
  private ArrayList<Legend> legends;

  public Users() {
  }

  public Users(String login, String password, Date date_of_check) {
    this.login = login;
    this.password = password;
    this.date_of_check = date_of_check;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getDate_of_check() {
    return date_of_check;
  }

  public void setDate_of_check(Date date_of_check) {
    this.date_of_check = date_of_check;
  }

  public ArrayList<Award> getAwards() {
    return awards;
  }

  public void setAwards(ArrayList<Award> awards) {
    this.awards = awards;
  }

  public ArrayList<Legend> getLegends() {
    return legends;
  }

  public void setLegends(ArrayList<Legend> legends) {
    this.legends = legends;
  }
}
