package ejb.data;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;

import entity.Users;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class UsersAccess extends Access {
  public void addNewUser(String login, String email, String password) throws ServletException {
    if (login == null || password == null) {
      throw new ServletException("Login or password is null");
    }

    Date date = new Date(Calendar.getInstance().getTime().getTime());

    create(login, password, date, email);
  }

  public void checkUser(String login, String password) throws ServletException {
    if (!password.equals(findUserPassword(login))) {
      throw new ServletException("Wrong password or login");
    }
  }

  public String findUserPassword(String login) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e.password from Users e where e.login = :login");
    query.setParameter("login", login);

    List<String> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }

  public int findUserId(String login) throws ServletException {
    EntityManager entityManager = generateEntityManager();

    Query query = entityManager.createQuery("Select e.id from Users e where e.login = :login");
    query.setParameter("login", login);

    List<Integer> list = query.getResultList();

    entityManager.close();

    if (list.size() == 0) {
      return -1;
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
    entityManager.close();
  }

  public Users read(int id) {
    EntityManager entityManager = generateEntityManager();
    Users user = entityManager.find(Users.class, id);

    entityManager.close();
    return user;
  }
}
