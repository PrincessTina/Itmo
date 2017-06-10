package com.company;

import com.google.gson.Gson;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

class CollectionController {
  /**
   * Reads saved collection from server
   *
   * @return collection to be worked with
   */
  static ArrayList<Shorty> readFromServer(String arrayOfObjects) throws Exception {
    ArrayList<Shorty> searchedCollection = new ArrayList<>();
    Gson gson = new Gson();

    arrayOfObjects = arrayOfObjects.substring(1, arrayOfObjects.length() - 1) + ",";

    for (String tokens : arrayOfObjects.split("%,")) {
      searchedCollection.add(gson.fromJson(tokens, Shorty.class));
    }

    Comparator<Shorty> shortyComparator = new Shorty();
    searchedCollection.sort(shortyComparator);

    return searchedCollection;
  }

  /**
   * Saves current conditions to file
   *
   * @param peopleFileName - backup file name
   * @param people         - collection to be saved
   */
  static void writeToFile(String peopleFileName, ArrayList<Shorty> people) {
    try (PrintWriter pw = new PrintWriter(peopleFileName)) {
      for (Shorty shorty : people) {
        pw.print(shorty.toString());
        pw.print("; ");
      }
    } catch (Exception ex) {
      System.out.println("Oops: " + ex.getMessage());
    }
  }

  /**
   * Returns last modification date, collection size and collection type.
   *
   * @param people         - collection, where info is gathered
   * @param peopleFileName - file name, from which the collection is initialized
   */
  static String info(ArrayList<Shorty> people, String peopleFileName) {
    return "Collection type: " + people.getClass().getTypeName() + ", Update time: " +
        getLastModificationDate(peopleFileName) + ", Size: " + people.size();
  }

  /**
   * Removes first collection's element
   *
   * @param people - collection to be interacted with
   */
  static void remove_first(ArrayList<Shorty> people) throws Exception {
    people.remove(0);
  }

  /**
   * Removes all elements, which are equals to  the element
   *
   * @param people     - collection to be interacted with
   * @param shortyJson - shorty pattern by which will delete from collection
   */
  static int remove_all(ArrayList<Shorty> people, String shortyJson) throws Exception {
    Gson gson = new Gson();
    Shorty example_object = gson.fromJson(shortyJson, Shorty.class);
    ArrayList<Integer> indexArray = new ArrayList<>();

    // Finds people indexes
    for (int a = 0; a < people.size(); a++) {
      if (people.get(a).compare(people.get(a), example_object) == 0) {
        indexArray.add(a);
      }
    }

    // Removes persons from collection
    for (int a = 0, m = 0; a < indexArray.size(); a++, m++) {
      int c;
      c = indexArray.get(a) - m;
      people.remove(c);
    }

    return indexArray.size();
  }

  private static String getLastModificationDate(String peopleFileName) {
    Date lastModified = new Date(new File(peopleFileName).lastModified());
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    return formatter.format(lastModified);
  }
}
