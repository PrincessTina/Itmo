package ejb.data;

import entity.Author;

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
public class AuthorAccess extends Access {
  public void create(String name, String surname) {
    EntityManager entityManager = generateEntityManager();
    Author row = new Author(name, surname);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  /**
   *
   * @param name
   * @param surname
   * @return id or -1
   */
  public int findAuthor(String name, String surname) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e.id from Author e where e.name = :name and e.surname = :surname order by e.id desc");
    query.setParameter("name", name);
    query.setParameter("surname", surname);

    List<Integer> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return -1;
    } else {
      return list.get(0);
    }
  }
}
