package ejb.data;

import entity.Artifact;

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
public class ArtifactAccess extends Access {
  public List<entity.Character> getCharacters(String description) throws ServletException {
    Artifact artifact = find(description);

    return artifact.getCharacters();
  }

  public Artifact find(String description) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Artifact e where e.description = :description");
    query.setParameter("description", description);

    List<Artifact> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }

  public List<Artifact> getAllList() throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e from Artifact e");

    List<Artifact> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list;
    }
  }
}
