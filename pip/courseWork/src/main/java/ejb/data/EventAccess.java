package ejb.data;

import entity.Event;
import entity.Phrase;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.List;

@Stateless
@LocalBean
public class EventAccess extends Access {
  public Phrase getAnswer(String description) throws ServletException {
    Event event = find(description);

    return event.getAnswer();
  }

  public Event find(String description) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Event e where e.description = :description");
    query.setParameter("description", description);

    List<Event> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }
}
