package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;

import entity.Users;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Stateless
public class UsersAccess extends Access {
  public void addNewUser(String login, String email, String password) {
    Date date = new Date(Calendar.getInstance().getTime().getTime());

    create(login, password, date, email);
  }

  public void checkUser(String login, String password) throws ServletException {
    if (!password.equals(findUser(login))) {
      throw new ServletException("Wrong password or login");
    }
  }

  /**
   *
   * @param login
   * @return password
   */
  public String findUser(String login) {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e.password from Users e where e.login = :login");
    query.setParameter("login", login);

    List<String> list = query.getResultList();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }

  private void create(String login, String password, Date date_of_check, String email) {
    EntityManager entityManager = generateEntityManager();
    Users row = new Users(login, email, password, date_of_check);

    entityManager.getTransaction().begin();
    entityManager.persist(row);
    entityManager.getTransaction().commit();
  }

  private Users read(int id) {
    EntityManager entityManager = generateEntityManager();

    return entityManager.find(Users.class, id);
  }

  private void delete(int id) {
    EntityManager entityManager = generateEntityManager();

    entityManager.getTransaction().begin();
    entityManager.remove(read(id));
    entityManager.getTransaction().commit();
  }

  private void update(int id, String login, String password, String email, Date date_of_check) {
    EntityManager entityManager = generateEntityManager();
    Users row = read(id);

    entityManager.getTransaction().begin();
    row.setEmail(email);
    row.setDate_of_check(date_of_check);
    row.setLogin(login);
    row.setPassword(password);
    entityManager.getTransaction().commit();
  }
}
