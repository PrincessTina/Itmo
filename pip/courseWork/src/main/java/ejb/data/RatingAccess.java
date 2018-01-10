package ejb.data;

import entity.Rating;

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
public class RatingAccess extends Access {
  public int getGrade(int object_id) throws ServletException {
    Rating rating = find(object_id);

    int count = rating.getOne() + rating.getTwo() + rating.getThree() + rating.getFour() + rating.getFive();

    return (rating.getOne() + rating.getTwo() * 2 + rating.getThree() * 3 + rating.getFour() * 4 + rating.getFive() * 5) / count;
  }

  public Rating find(int object_id) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Rating e where e.object_id = :object_id");
    query.setParameter("object_id", object_id);

    List<Rating> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }
}
