package context;

import ejb.ImageAccess;
import entity.Image;
import entity.Users;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Stateless
public class ContextAccess {

  @EJB
  private ImageAccess images;

  public Users getUserFromContext(HttpServletRequest request) {
    return (Users) request.getSession().getAttribute("user");
  }

  public void addUserInContext(String login, String password, HttpServletRequest request) {
    Users user = new Users();

    user.setLogin(login);
    user.setName("");
    user.setPassword(password);

    ArrayList<Integer> listOfId = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13));
    ArrayList<Image> image = images.readInitImages(listOfId);
    int iconId = new Random().nextInt(listOfId.size());

    user.setIcon(image.get(iconId).getLink());

    request.getSession().setAttribute("user", user);
  }

  public void removeUserFromContext(HttpServletRequest request) {
    Users user = new Users();

    user.setLogin("");
    user.setName("");
    user.setIcon("");
    user.setPassword("");

    request.getSession().setAttribute("user", user);
  }

  public void setIcon(String link, HttpServletRequest request) {
    Users user = getUserFromContext(request);
    user.setIcon(link);

    request.getSession().setAttribute("user", user);
  }

  public void addNameInContext(String name, HttpServletRequest request) {
    Users user = getUserFromContext(request);
    user.setName(name);

    request.getSession().setAttribute("user", user);
  }
}
