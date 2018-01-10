package classes;

public class Notification {
  private String type;
  private String description;

  public Notification(String type, String body) {
    this.description = body;
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
