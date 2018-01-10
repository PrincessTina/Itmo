package ejb.data;

import entity.Note;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Calendar;

@Stateless
@LocalBean
public class NoteAccess extends Access {
  public void create(int owner_id, int image_id, String description) {
    EntityManager entityManager = generateEntityManager();
    Date date = new Date(Calendar.getInstance().getTime().getTime());

    Note row = new Note(owner_id, image_id, date, description);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
