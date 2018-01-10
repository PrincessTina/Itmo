package ejb.logic;

import ejb.data.ImageAccess;
import ejb.data.NoteAccess;
import entity.Image;
import entity.Note;
import jms.notifications.Notification;
import jms.notifications.NotificationTypes;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Crud уведомлений
 */
@Stateless
public class NotificationAccess {

  @EJB
  NoteAccess noteAccess;

  @EJB
  ImageAccess imageAccess;

  /**
   * Создает уведомление, подтягивая из базы всё необходимое
   */
  public Notification createById(int id, String type) throws Exception {
    String body;
    String imageUrl;

    if (type.equals(NotificationTypes.NEWS)) {
      Note note = noteAccess.getById(id);
      Image noteImage = imageAccess.read(note.getImage_id());

      body = note.getDescription();
      imageUrl = noteImage.getLink();
    } else {
      throw new Exception("Undefined notification type");
    }

    return new Notification(id, type, body, imageUrl);
  }
}
