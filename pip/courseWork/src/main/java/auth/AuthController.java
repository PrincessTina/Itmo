package auth;

import context.ContextController;
import controllers.UsersController;
import ejb.UsersAccess;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@WebServlet(name = "auth", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {
  @EJB
  private UsersAccess users;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String code = request.getParameter("code");
    String service = request.getParameter("address").substring(0, 2);
    String address = request.getParameter("address").substring(2);


    if (code == null) {
      response.sendRedirect(address);
    } else {
      if (service.equals("vk")) {
        authorizeWithVk(code, response, service, address, request);
      } else {
        throw new ServletException("Unknown service");
      }
    }
  }

  private void authorizeWithVk(String code, HttpServletResponse response, String service, String address,
                               HttpServletRequest request) throws IOException, ServletException {
    String email;
    String accessToken = null;
    int userId = 0;

    try {
      JSONObject link = getResponseFromApi("https://oauth.vk.com/access_token?" +
          "client_id=6323215&" +
          "client_secret=jWRjPW1pjkZbHGJ1SCHN&" +
          "code=" + code + "&" +
          "redirect_uri=localhost:62434/courseWork-12295115019915848166.0-SNAPSHOT/" +
          "auth?address=" + service + address);

      userId = link.getInt("user_id");
      accessToken = link.getString("access_token");
      email = link.getString("email");

    } catch (JSONException ex) {
      if (ex.getMessage().equals("JSONObject[\"email\"] not found.")) {
        email = null;
      } else {
        throw new ServletException(ex.getMessage());
      }
    }

    try {
      response.sendRedirect(address);

      JSONObject link = getResponseFromApi("https://api.vk.com/method/users.get?" +
          "uids=" + userId + "&" +
          "fields=first_name,screen_name,photo_50&" +
          "access_token=" + accessToken);

      registerUser(link, email, service, request);
    } catch (JSONException ex) {
      throw new ServletException(ex.getMessage());
    }
  }

  private void registerUser(JSONObject link, String email, String service, HttpServletRequest request)
      throws JSONException, ServletException{
    ContextController context = new ContextController();

    JSONObject arrayOfProfileAttributes = (JSONObject) link.getJSONArray("response").get(0);

    String screenName = arrayOfProfileAttributes.getString("screen_name");
    String name = arrayOfProfileAttributes.getString("first_name");

    String login = createLogin(screenName, service);
    String password = findUser(login);

    if (password == null) {
      password = createPassword();
      tryAddUser(login, password, email);
    }

    context.addUserInContext(login, password, request);
    context.addNameInContext(name, request);
  }

  private String createLogin(String screenName, String service) {
    return service.toUpperCase() + screenName;
  }

  private String createPassword() {
    ArrayList list = new ArrayList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, "asa", "ber", "c", "qby", "!", "yho", "s", "r", "xe"));
    StringBuilder password = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < 10; i++) {
      password.append(list.get(random.nextInt(17)));
    }

    return password.toString();
  }

  private String findUser(String login) throws ServletException {
   return users.findUser(login);
  }

  private void tryAddUser(String login, String password, String email) {
    users.addNewUser(login, email, password);
  }

  private JSONObject getResponseFromApi(String link) throws IOException, JSONException {
    StringBuilder builder = new StringBuilder();
    String line;
    URL url = new URL(link);
    URLConnection connection = url.openConnection();
    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    JSONObject jsonObject;

    while ((line = reader.readLine()) != null) {
      builder.append(line);
    }

    jsonObject = new JSONObject(builder.toString());
    return jsonObject;
  }
}
