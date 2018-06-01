package ejb.data;

import entity.Award;
import entity.Users;

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
public class AwardAccess extends Access {
  public List<Users> getUsers(String description) throws ServletException {
    Award award = find(description);

    return award.getUsers();
  }

  public Award find(String description) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Award e where e.description = :description");
    query.setParameter("description", description);

    List<Award> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }
}
