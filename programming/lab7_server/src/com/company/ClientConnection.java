package com.company;

import com.google.gson.Gson;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

class ClientConnection {
  static DatagramSocket socket;
  static Connection connection;
  private static ArrayList<Shorty> people = new ArrayList<>();
  private static String stringForNewCollection;
  private static String stringWithOldObject;

  // ToDo: Прописать дату последней модификации коллекции на сервере и обеспечить ее сопоставление с датами обращений клиентов
  // ToDo: Исправить баг с изменением всех одинаковых объектов (проблема в функции)
  static void waitingCommand() {
    try {
      DatagramPacket receivePacket;
      String receiveData;
      byte[] sendData;
      byte[] sendSize;

      receivePacket = new DatagramPacket(new byte[30], 30);
      socket.receive(receivePacket);
      receiveData = new String(receivePacket.getData());

      if (receiveData.contains("load")) {
        read();
        sendData = people.toString().getBytes();
        sendSize = String.valueOf(sendData.length).getBytes();

        socket.send(new DatagramPacket(sendSize, sendSize.length, receivePacket.getAddress(),
            receivePacket.getPort()));

        socket.send(new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
            receivePacket.getPort()));
      } else if (receiveData.contains("modify")) {
        sendData = "1".getBytes();
        socket.send(new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
            receivePacket.getPort()));

        receivePacket = new DatagramPacket(new byte[people.toString().getBytes().length],
            people.toString().getBytes().length);
        socket.receive(receivePacket);
        stringForNewCollection = new String(receivePacket.getData(), 0, receivePacket.getLength());
        socket.receive(receivePacket);
        stringWithOldObject = new String(receivePacket.getData(), 0, receivePacket.getLength());
        modify();
      } else {
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private static void read() throws Exception {
    if (people.size() != 0) {
      people.clear();
    }
    Statement statement = connection.createStatement();
    CachedRowSet resultSet = new CachedRowSetImpl();
    resultSet.populate(statement.executeQuery("SELECT * FROM shorty"));
    Status status;

    while (resultSet.next()) {
      switch (resultSet.getString(5)) {
        case "married":
          status = Status.married;
          break;
        case "idle":
          status = Status.idle;
          break;
        case "have_a_girlfriend":
          status = Status.have_a_girlfriend;
          break;
        case "single":
          status = Status.single;
          break;
        default:
          status = Status.all_is_complicated;
      }
      people.add(new Shorty(resultSet.getString(1), resultSet.getInt(2),
          resultSet.getDouble(3), resultSet.getString(4), status));
    }
  }

  private static ArrayList<Shorty> parsing(String string) throws Exception {
    ArrayList<Shorty> searchedCollection = new ArrayList<>();
    Gson gson = new Gson();
    String arrayOfObjects = string.substring(0, string.length());

    for (String tokens : arrayOfObjects.split("%")) {
      searchedCollection.add(gson.fromJson(tokens, Shorty.class));
    }
    return searchedCollection;
  }

  private static void modify() throws Exception {
    ArrayList<Shorty> newCollection = parsing(stringForNewCollection);
    ArrayList<Shorty> oldCollection = parsing(stringWithOldObject);
    Shorty newShorty = newCollection.get(0), oldShorty = oldCollection.get(0);
    String request = "select modify(?::text, ?::integer, ?::decimal, ?::text, ?::text, " +
        "?::text, ?::integer, ?::decimal, ?::text, ?::text);";
    CallableStatement statement = connection.prepareCall(request);

    statement.setString(1, newShorty.name);
    statement.setInt(2, newShorty.age);
    statement.setDouble(3, newShorty.height);
    statement.setString(4, newShorty.hobby);
    statement.setString(5, switchStatus(newShorty.status));
    statement.setString(6, oldShorty.name);
    statement.setInt(7, oldShorty.age);
    statement.setDouble(8, oldShorty.height);
    statement.setString(9, oldShorty.hobby);
    statement.setString(10, switchStatus(oldShorty.status));
    statement.execute();
  }

  private static String switchStatus(Status status) {
    String statusString;
    switch (status) {
      case all_is_complicated:
        statusString = "all_is_comlicated";
        break;
      case have_a_girlfriend:
        statusString = "have_a_girlfriend";
        break;
      case married:
        statusString = "married";
        break;
      case single:
        statusString = "single";
        break;
      default:
        statusString = "idle";
    }
    return statusString;
  }
}
