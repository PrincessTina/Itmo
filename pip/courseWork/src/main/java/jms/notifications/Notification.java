package jms.notifications;

public class Notification {
  int id;
  String type;
  String body;
  String imageUrl;

  public Notification(int id, String type) {
    this.id = id;
    this.type = type;

    if (type.equals(NotificationTypes.NEWS)) {
      // Retrieve news from crud
      this.body = "new news from devs!";
      this.imageUrl = "";
    }
  }
}
