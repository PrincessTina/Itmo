package ejb.notification;

import ejb.context.ContextAccess;
import ejb.data.ImageAccess;
import ejb.data.NoteAccess;
import ejb.data.UsersAccess;
import entity.Users;

import javax.ejb.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class NotificationLogic {
  @EJB
  private ImageAccess images;

  @EJB
  private ContextAccess context;

  @EJB
  private UsersAccess users;

  @EJB
  private NoteAccess notes;

  public void addNewNote(String link, String description, HttpServletRequest request) throws ServletException {
    int image_id = getNewImageId(link);
    int owner_id = getCurrentUserId(request);

    notes.create(owner_id, image_id, description);
  }

  private int getNewImageId(String link) throws ServletException {
    images.create(link);

    return images.findImage(link);
  }

  private int getCurrentUserId(HttpServletRequest request) throws ServletException {
    Users currentUser = context.getUserFromContext(request);
    String login = currentUser.getLogin();

    return users.findUserId(login);
  }
}
