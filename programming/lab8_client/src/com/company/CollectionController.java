package com.company;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

class CollectionController {
  static String lastModified = "null";

  /**
   * Reads saved collection from server
   *
   * @return collection to be worked with
   */
  static ArrayList<Shorty> readFromServer(String arrayOfObjects) throws Exception {
    ArrayList<Shorty> searchedCollection = new ArrayList<>();
    Gson gson = new Gson();
    String date;
    int i = 0;

    arrayOfObjects = arrayOfObjects.substring(1, arrayOfObjects.length() - 1) + ",";

    for (String tokens : arrayOfObjects.split("%,")) {
      date = tokens.substring(tokens.indexOf("date") - 2, tokens.length() - 2).substring(9);
      tokens = tokens.substring(0, tokens.indexOf("date") - 2) + "}";
      searchedCollection.add(gson.fromJson(tokens, Shorty.class));
      searchedCollection.get(i).date = ZonedDateTime.parse(date);
      i++;
    }

    Comparator<Shorty> shortyComparator = new Shorty();
    searchedCollection.sort(shortyComparator);

    return searchedCollection;
  }

  /**
   * Returns last modification date, collection size and collection type.
   *
   * @param people         - collection, where info is gathered
   */
  static String info(ArrayList<Shorty> people) {
    return "Collection type: " + people.getClass().getTypeName() + ", Update time: " + lastModified
        + ", Size: " + people.size();
  }

  /**
   * Removes first collection's element
   *
   * @param people - collection to be interacted with
   */
  static void remove_first(ArrayList<Shorty> people) throws Exception {
    people.remove(0);
    getLastModificationDate();
  }

  /**
   * Removes all elements, which are equals to  the element
   *
   * @param people     - collection to be interacted with
   * @param shortyJson - shorty pattern by which will delete from collection
   */
  static String remove_all(ArrayList<Shorty> people, String shortyJson) throws Exception {
    Gson gson = new Gson();
    Shorty example_object = gson.fromJson(shortyJson, Shorty.class);
    ArrayList<Integer> indexArray = new ArrayList<>();
    StringBuilder indexes = new StringBuilder();

    // Finds people indexes
    for (int a = 0; a < people.size(); a++) {
      if (people.get(a).compare(people.get(a), example_object) == 0) {
        indexArray.add(a);
        indexes.append(people.get(a).id).append("%");
      }
    }

    // Removes persons from collection
    for (int a = 0, m = 0; a < indexArray.size(); a++, m++) {
      int c;
      c = indexArray.get(a) - m;
      people.remove(c);
    }

    return indexes.toString();
  }

  static void getLastModificationDate() {
    Date date =  new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    lastModified = formatter.format(date);
  }
}
