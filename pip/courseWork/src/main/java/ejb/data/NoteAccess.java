package ejb.data;

import entity.Note;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class NoteAccess extends Access {
  @EJB
  private ImageAccess images;

  public void addNewNote(String link, String description) throws ServletException {
    int image_id = getNewImageId(link);

    EntityManager entityManager = generateEntityManager();
    Date date = new Date(Calendar.getInstance().getTime().getTime());

    Note row = new Note(image_id, date, description);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  private int getNewImageId(String link) throws ServletException {
    images.create(link);

    return images.findImage(link);
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
