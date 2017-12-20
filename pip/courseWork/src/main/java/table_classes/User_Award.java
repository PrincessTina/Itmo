package table_classes;

import javax.persistence.Entity;

@Entity
public class User_Award {
  private int user_id;
  private int award_id;

  public User_Award() {
  }

  public User_Award(int user_id, int award_id) {
    this.user_id = user_id;
    this.award_id = award_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public int getAward_id() {
    return award_id;
  }

  public void setAward_id(int award_id) {
    this.award_id = award_id;
  }
}
