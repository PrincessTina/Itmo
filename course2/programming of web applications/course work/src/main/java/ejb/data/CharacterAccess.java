package ejb.data;

import entity.Character;

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
public class CharacterAccess extends Access {
  public Character find(String name) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Character e where e.name = :name");
    query.setParameter("name", name);

    List<Character> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }

  public void create(String name, String type, String description) throws ServletException {
    EntityManager entityManager = generateEntityManager();
    Character row = new Character(name, type, description);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  public List<Character> getAll() {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Character e");
    List<Character> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list;
    }
  }
}
