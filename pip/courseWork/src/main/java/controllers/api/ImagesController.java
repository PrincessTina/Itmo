package controllers.api;

import com.google.gson.Gson;
import models.cruds.ImagesCrud;
import models.entities.Image;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "images", urlPatterns = {"/images"})
public class ImagesController extends HttpServlet {

  @EJB
  private ImagesCrud images;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    ArrayList<Image> images = getCarouselImages();
    String imagesJson = new Gson().toJson(images);

    try {
      response.getWriter().write(imagesJson);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Todo: Add pretty imageIndexes initialization
   */
  private ArrayList<Image> getCarouselImages() {
    ArrayList<Integer> imageIndexes = new ArrayList<Integer>();
    imageIndexes.add(0);
    imageIndexes.add(1);
    imageIndexes.add(2);
    imageIndexes.add(3);
    imageIndexes.add(4);

    return images.readInitImages(imageIndexes);
  }
}
