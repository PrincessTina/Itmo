package jms.notifications;

/**
 * Уведомление, по которому Gson делает json для фронтенда
 */
public class Notification {
  private int id;
  private String type;
  private String body;
  private String imageUrl;

  public Notification(int id, String type, String body, String imageUrl) {
    this.id = id;
    this.type = type;
    this.body = body;
    this.imageUrl = imageUrl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
