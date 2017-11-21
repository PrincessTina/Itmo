package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Math.pow;

@WebServlet(name = "checker", urlPatterns = {"/checker"})
public class AreaCheckServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ServletContext context = getServletContext();
    String result = null;

    try {
      double x = Double.parseDouble(String.valueOf(context.getAttribute("x")));
      double y = Double.parseDouble(String.valueOf(context.getAttribute("y")));
      double r = Double.parseDouble(String.valueOf(context.getAttribute("r")));

      if (checkParams(x, y, r)) {
        if (checkPoint(x, y, r)) {
          result = "Point is in the scope";
        } else {
          result = "Point is out of the scope";
        }
      }
      context.setAttribute("result", result);

      //add new points
      if (context.getAttribute("points") == null) {
        context.setAttribute("points", x + "," + y + "," + r + "," + result);
      } else {
        context.setAttribute("points", context.getAttribute("points") + ";"
            + x + "," + y + "," + r + "," + result);
      }
    } catch (Exception ex) {
      context.setAttribute("x", "even try");
      context.setAttribute("y", "do this");
      context.setAttribute("r", "Don't");
      context.setAttribute("result", "!");
    }

    request.getRequestDispatcher("/result.jsp").forward(request, response);
  }

  private boolean checkPoint(double x, double y, double r) {
    if ((pow(x, 2) + pow(y, 2) <= pow(r, 2)) && (x >= -r) && (x <= 0) && (y >= -r) && (y <= 0)) {
      return true;
    }

    if ((x <= r) && (x >=0) && (y <=0) && (y >= -r)) {
      return true;
    }

    if ((y <= r - x) && (x >=0) && (x <= r) && (y >=0) && (y <= r)) {
      return true;
    }

    return false;
  }

  private boolean checkParams(double x, double y, double r) {
    return  !((x >= 5) || (x <= -5) || (r >= 4) || (r <= 1) || ((int)y >= 5) || ((int)y <= -5) ||
        (!String.valueOf(x).matches("^-?\\d+\\.?\\d*$")) || (!String.valueOf(y).matches("^-?\\d+\\.?\\d*$")) ||
        (!String.valueOf(r).matches("^-?\\d+\\.?\\d*$")));
  }
}
