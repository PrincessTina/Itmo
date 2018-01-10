package controllers.data;

import com.google.gson.Gson;
import ejb.data.CountryAccess;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "countries", urlPatterns = {"/countries"})
public class CountryController extends HttpServlet {

  @EJB
  private CountryAccess countries;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String action = request.getParameter("action");
      String name = request.getParameter("name");
      String answer;

      if (action == null) {
        throw new ServletException("Action is null");
      } else if (action.equals("country")) {
        answer = new Gson().toJson(countries.find(name));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      } else if (action.equals("images")) {
        answer = new Gson().toJson(countries.getImages(name));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      } else if(action.equals("legends")) {
        answer = new Gson().toJson(countries.getLegends(name));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      }
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
