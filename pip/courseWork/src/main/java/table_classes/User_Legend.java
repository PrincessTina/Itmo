package table_classes;

import javax.persistence.Entity;

@Entity
public class User_Legend {
  private int user_id;
  private int legend_id;

  public User_Legend() {
  }

  public User_Legend(int user_id, int legend_id) {
    this.user_id = user_id;
    this.legend_id = legend_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public int getLegend_id() {
    return legend_id;
  }

  public void setLegend_id(int legend_id) {
    this.legend_id = legend_id;
  }
}
