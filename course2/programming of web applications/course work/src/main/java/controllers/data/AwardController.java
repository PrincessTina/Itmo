package controllers.data;

import com.google.gson.Gson;
import ejb.data.AwardAccess;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "awards", urlPatterns = {"/awards"})
public class AwardController extends HttpServlet {
  @EJB
  private AwardAccess awards;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String description = request.getParameter("description");
      String answer = new Gson().toJson(awards.getUsers(description));

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(answer);

    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
