package controllers.data;

import com.google.gson.Gson;
import ejb.data.CountryAccess;
import entity.Country;

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
      int id = Integer.parseInt(request.getParameter("id"));
      Country country = countries.read(id);

      String answer = new Gson().toJson(country);

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(answer);
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
