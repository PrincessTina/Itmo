package auth;

import context.ContextAccess;
import ejb.UsersAccess;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Stateless
public class AuthLogic {

  @EJB
  ContextAccess context;

  @EJB
  UsersAccess users;

  public void authorizeWithVk(String code, HttpServletResponse response, String service, String address,
                               HttpServletRequest request) throws IOException, ServletException {
    String email;
    String accessToken = null;
    int userId = 0;

    try {
      JSONObject link = getResponseFromApi("https://oauth.vk.com/access_token?" +
          "client_id=6323215&" +
          "client_secret=jWRjPW1pjkZbHGJ1SCHN&" +
          "code=" + code + "&" +
          "redirect_uri=" + address +
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
          "fields=first_name,screen_name,photo_200&" +
          "access_token=" + accessToken);

      registerUserWithVk(link, email, service, request);
    } catch (JSONException ex) {
      throw new ServletException(ex.getMessage());
    }
  }

  public void authorizeWithIn(String code, HttpServletResponse response, String service, String address,
                               HttpServletRequest request) throws IOException, ServletException {
    JSONObject user;

    try {
      JSONObject link = getFromPostCall(code, address, service);

      response.sendRedirect(address);

      user = link.getJSONObject("user");

      String username = user.getString("username");
      String icon = user.getString("profile_picture");
      String login = createLogin(username, service);
      String password = users.findUser(login);

      if (password == null) {
        password = createPassword();
        users.addNewUser(login, password, "null");
      }

      context.addUserInContext(login, password, request);
      context.addNameInContext(username, request);
      context.setIcon(icon, request);
    } catch (JSONException ex) {
      throw new ServletException(ex.getMessage());
    }
  }

  private void registerUserWithVk(JSONObject link, String email, String service, HttpServletRequest request)
      throws JSONException, ServletException, IOException {
    JSONObject arrayOfProfileAttributes = (JSONObject) link.getJSONArray("response").get(0);

    String screenName = arrayOfProfileAttributes.getString("screen_name");
    String name = arrayOfProfileAttributes.getString("first_name");
    String icon = arrayOfProfileAttributes.getString("photo_200");

    String login = createLogin(screenName, service);
    String password = users.findUser(login);

    if (password == null) {
      password = createPassword();
      users.addNewUser(login, password, email);
    }

    context.addUserInContext(login, password, request);
    context.addNameInContext(name, request);
    context.setIcon(icon, request);
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

    jsonObject = new JSONObject(URLDecoder.decode(URLEncoder.encode(builder.toString(), "iso8859-1"), "UTF-8"));
    return jsonObject;
  }

  private JSONObject getFromPostCall(String code, String address, String service) throws IOException, JSONException {
    StringBuilder builder = new StringBuilder();
    String line;
    URL url = new URL("https://api.instagram.com/oauth/access_token?");
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    BufferedReader reader;
    JSONObject jsonObject;

    connection.setRequestMethod("POST");
    String urlParameters = "client_id=39649a7b64a54a898f1f972c68eb1e26&" +
        "client_secret=7bfd8e758f8b4626b5cc50572d9ab54f&" +
        "grant_type=authorization_code&" +
        "code=" + code + "&" +
        "redirect_uri=" + address +
        "auth?address=" + service + address;

    // Send post request
    connection.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.flush();
    wr.close();

    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

    while ((line = reader.readLine()) != null) {
      builder.append(line);
    }

    jsonObject = new JSONObject(URLDecoder.decode(URLEncoder.encode(builder.toString(), "iso8859-1"), "UTF-8"));

    return jsonObject;
  }
}
