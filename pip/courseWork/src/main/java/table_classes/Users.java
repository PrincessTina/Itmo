package table_classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Users {
  @Id
  @GeneratedValue
  private int id;
  private String login;
  private String password;
  private Date date_of_check;

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
}
