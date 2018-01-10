package classes;

public class Notification {
  private String type;
  private String body;

  public Notification(String type, String body) {
    this.body = body;
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
