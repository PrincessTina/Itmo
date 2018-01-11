package ejb.data;

import ejb.context.ContextAccess;
import ejb.data.Access;
import ejb.data.ImageAccess;
import ejb.data.UsersAccess;
import entity.Note;
import entity.Users;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class NoteAccess extends Access {
  @EJB
  private ImageAccess images;

  @EJB
  private ContextAccess context;

  @EJB
  private UsersAccess users;

  public void addNewNote(String link, String description, HttpServletRequest request) throws ServletException {
    int image_id = getNewImageId(link);
    int owner_id = getCurrentUserId(request);

    EntityManager entityManager = generateEntityManager();
    Date date = new Date(Calendar.getInstance().getTime().getTime());

    Note row = new Note(owner_id, image_id, date, description);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
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

  public List<Note> getAllNotes() {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Note e order by e.date desc");
    List<Note> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      for (int i = 0; i < list.size(); i++) {
        Note note = list.get(i);
        note.setImage(images.read(note.getImage_id()));

        list.set(i, note);
      }

      return list;
    }
  }
}
