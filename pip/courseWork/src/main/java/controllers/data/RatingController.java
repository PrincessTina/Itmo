package controllers.data;

import com.google.gson.Gson;
import ejb.data.RatingAccess;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "rating", urlPatterns = {"/rating"})
public class RatingController extends HttpServlet {

  @EJB
  private RatingAccess rating;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String action = request.getParameter("action");
      int object_id = Integer.parseInt(request.getParameter("object_id"));
      String answer;

      if (action == null) {
        throw new ServletException("Action is null");
      } else if (action.equals("rating")) {
        answer = new Gson().toJson(rating.find(object_id));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      } else if (action.equals("grade")) {
        answer = new Gson().toJson(rating.getGrade(object_id));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      }
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
