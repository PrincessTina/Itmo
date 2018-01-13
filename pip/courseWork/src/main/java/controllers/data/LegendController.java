package controllers.data;

import com.google.gson.Gson;
import ejb.data.Controller;
import ejb.data.LegendAccess;
import entity.Legend;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "legends", urlPatterns = {"/legends"})
public class LegendController extends HttpServlet {

  @EJB
  private LegendAccess legends;

  @EJB
  private Controller controller;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      JSONObject jsonObject = controller.getAnswerFromPost(request);

      if (!jsonObject.getString("description").equals("like")) {
        String link = jsonObject.getString("link");
        String description = jsonObject.getString("description");
        String name = jsonObject.getString("name");
        int country_id = Integer.parseInt(jsonObject.getString("country_id"));

        legends.create(link, name, description, country_id);
      }
    } catch (JSONException e) {
      throw new ServletException("Error parsing JSON request string ");
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      JSONObject jsonObject = controller.getAnswerFromPost(request);

      int id = Integer.parseInt(jsonObject.getString("id"));
      int user_id = Integer.parseInt(jsonObject.getString("user_id"));

      legends.setLike(id, user_id);
    } catch (Exception ex) {
      throw new ServletException(Arrays.toString(ex.getStackTrace()) + ex.getMessage() + legends + controller);
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      if (request.getParameter("id").equals("all")) {
        List<Legend> array = legends.getTop();

        for (int i = 0; i < array.size(); i++) {
          Legend legend = array.get(i);

          legend.setUsers(legends.getUsers(legend.getId()));
          legend.setRating(legend.getRating() + legend.getUsers().size());

          array.set(i, legend);
        }

        String answer = new Gson().toJson(array);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      } else {
        int id = Integer.parseInt(request.getParameter("id"));
        Legend legend = legends.read(id);
        legend.setUsers(legends.getUsers(id));
        legend.setRating(legend.getRating() + legend.getUsers().size());

        String answer = new Gson().toJson(legend);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      }
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
