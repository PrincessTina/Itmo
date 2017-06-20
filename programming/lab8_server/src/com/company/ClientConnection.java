package com.company;

import com.google.gson.Gson;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.net.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

class ClientConnection {
  static DatagramSocket socket;
  static Connection connection;
  private static String lastModified = "null";
  private static ArrayList<Shorty> people = new ArrayList<>();
  private static String stringForNewCollection;
  static String stringWithIndexes;

  static void waitingCommand() {
    try {
      DatagramPacket receivePacket;
      String receiveData, receiveDate, answerOnDate;
      byte[] sendData;
      byte[] sendSize;

      receivePacket = new DatagramPacket(new byte[30], 30);
      socket.receive(receivePacket);
      receiveData = new String(receivePacket.getData());
      socket.receive(receivePacket);
      receiveDate = new String(receivePacket.getData(), 0, receivePacket.getLength());

      if (lastModified.compareTo(receiveDate) <= 0) {
        answerOnDate = "1";
      } else {
        answerOnDate = "0";
      }

      sendData = answerOnDate.getBytes();
      socket.send(new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
          receivePacket.getPort()));

      if (receiveData.contains("load")) {
        read();
        sendData = people.toString().getBytes();
        sendSize = String.valueOf(sendData.length).getBytes();

        socket.send(new DatagramPacket(sendSize, sendSize.length, receivePacket.getAddress(),
            receivePacket.getPort()));

        socket.send(new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
            receivePacket.getPort()));
        getLastModificationDate();
      } else if ((receiveData.contains("modify")) && (Objects.equals(answerOnDate, "1"))) {
        socket.receive(receivePacket);
        stringWithIndexes = new String(receivePacket.getData(), 0, receivePacket.getLength());

        receivePacket = new DatagramPacket(new byte[people.toString().getBytes().length],
            people.toString().getBytes().length);
        socket.receive(receivePacket);
        stringForNewCollection = new String(receivePacket.getData(), 0, receivePacket.getLength());
        modify();
        getLastModificationDate();
      } else if ((receiveData.contains("remove")) && (Objects.equals(answerOnDate, "1"))) {
        sendData = "1".getBytes();
        socket.send(new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
            receivePacket.getPort()));

        socket.receive(receivePacket);
        stringWithIndexes = new String(receivePacket.getData(), 0, receivePacket.getLength());
        if (stringWithIndexes.compareTo("") != 0) {
          remove();
          getLastModificationDate();
        }
      } else if ((receiveData.contains("add")) && (Objects.equals(answerOnDate, "1"))) {
        sendData = "1".getBytes();
        socket.send(new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
            receivePacket.getPort()));

        receivePacket = new DatagramPacket(new byte[200], 200);
        socket.receive(receivePacket);
        stringForNewCollection = new String(receivePacket.getData(), 0, receivePacket.getLength());
        add();
        getLastModificationDate();
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
    ArrayList<Integer> digit = new ArrayList<>();

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
      String date = resultSet.getString(7);
      for (String tokens: date.split("-")) {
        digit.add(Integer.parseInt(tokens));
      }
      people.add(new Shorty(resultSet.getString(1), resultSet.getInt(2),
          resultSet.getDouble(3), resultSet.getString(4), status, resultSet.getInt(6),
          ZonedDateTime.of(digit.get(0), digit.get(1), digit.get(2), digit.get(3), digit.get(4),
              digit.get(5), digit.get(6), ZoneId.of("Europe/Moscow"))));
      digit.clear();
    }
  }

  private static ArrayList<Shorty> parsingShorty(String string) throws Exception {
    ArrayList<Shorty> searchedCollection = new ArrayList<>();
    Gson gson = new Gson();
    String arrayOfObjects = string.substring(0, string.length());
    String date;
    int i = 0;

    for (String tokens : arrayOfObjects.split("%")) {
      date = tokens.substring(tokens.indexOf("date") - 2, tokens.length() - 2).substring(9);
      tokens = tokens.substring(0, tokens.indexOf("date") - 2) + "}";
      searchedCollection.add(gson.fromJson(tokens, Shorty.class));
      searchedCollection.get(i).date = ZonedDateTime.parse(date);
      i++;
    }
    return searchedCollection;
  }

  public static ArrayList<Integer> parsingIndexes(String string) throws Exception {
    ArrayList<Integer> searchedIndexes = new ArrayList<>();
    String arrayOfObjects = string.substring(0, string.length());

    for (String tokens : arrayOfObjects.split("%")) {
      searchedIndexes.add(Integer.parseInt(tokens));
    }
    return searchedIndexes;
  }

  private static void modify() throws Exception {
    ArrayList<Shorty> collection = parsingShorty(stringForNewCollection);
    ArrayList<Integer> indexes = parsingIndexes(stringWithIndexes);
    Shorty newShorty = collection.get(0);
    int index = indexes.get(0);
    String request = "select modify(?, ?, ?::decimal, ?, ?, ?);";
    CallableStatement statement = connection.prepareCall(request);

    statement.setString(1, newShorty.name);
    statement.setInt(2, newShorty.age);
    statement.setDouble(3, newShorty.height);
    statement.setString(4, newShorty.hobby);
    statement.setString(5, switchStatus(newShorty.status));
    statement.setInt(6, index);
    statement.execute();
  }

  private static void add() throws Exception {
    ArrayList<Shorty> collection = parsingShorty(stringForNewCollection);
    Shorty newShorty = collection.get(0);
    String request = "insert into shorty(name, age, height, hobby, status, date) values(?, ?, ?::decimal, ?, ?, ?);";
    CallableStatement statement = connection.prepareCall(request);

    statement.setString(1, newShorty.name);
    statement.setInt(2, newShorty.age);
    statement.setDouble(3, newShorty.height);
    statement.setString(4, newShorty.hobby);
    statement.setString(5, switchStatus(newShorty.status));
    statement.setString(6,
        newShorty.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-0")));
    statement.execute();
  }

  private static void remove() throws Exception {
    ArrayList<Integer> indexes = parsingIndexes(stringWithIndexes);
    String request1 = "delete from shorty where id = ?;";
    String request2 = "alter sequence shorty_seq restart with 0;";
    String request3 = "update shorty set id = nextval('shorty_seq');";
    CallableStatement statement = connection.prepareCall(request1);
    connection.setAutoCommit(false);

    for (int index: indexes) {
      statement.setInt(1, index);
      statement.execute();
    }
    connection.commit();
    connection.setAutoCommit(true);

    statement = connection.prepareCall(request2);
    statement.execute();

    statement = connection.prepareCall(request3);
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

  private static void getLastModificationDate() {
    Date date =  new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    lastModified = formatter.format(date);
  }
}
