package ejb.data;

import entity.Note;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class NoteAccess extends Access {

  @EJB
  private ImageAccess images;

  public void create(int owner_id, int image_id, String description) {
    EntityManager entityManager = generateEntityManager();
    Date date = new Date(Calendar.getInstance().getTime().getTime());

    Note row = new Note(owner_id, image_id, date, description);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
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
