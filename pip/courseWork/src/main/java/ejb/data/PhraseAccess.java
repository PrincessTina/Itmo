package ejb.data;

import entity.Event;
import entity.Phrase;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class PhraseAccess extends Access {
  public List<Phrase> getAnswers(String description) throws ServletException {
    Phrase question = find(description);

    return question.getAnswers();
  }

  public Phrase getQuestion (String description) throws ServletException {
    Phrase answer = find(description);

    return answer.getQuestion();
  }

  public Event getEvent(String description) throws ServletException {
    Phrase phrase = find(description);

    return phrase.getEvent();
  }

  public Phrase find(String description) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Phrase e where e.description = :description");
    query.setParameter("description", description);

    List<Phrase> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }
}
