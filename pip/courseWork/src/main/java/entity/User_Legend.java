package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User_Legend {
  @Id
  @GeneratedValue
  private int id;
  private int user_id;
  private int legend_id;

  public User_Legend() {}

  public User_Legend(int user_id, int legend_id) {
    this.legend_id = legend_id;
    this.user_id = user_id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser_id() {
    return user_id;
  }
}
