package com.company;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

class ClientConnection {
  static DatagramSocket socket;
  private static String lastModified = "null";

  static void waitingCommand() {
    try {
      DatagramPacket receivePacket;
      String receiveData, receiveDate, answerOnDate, stringWithIndexes;
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
        ArrayList<Object> shorties = ORM.read("com.company.Shorty");

        sendData = shorties.toString().getBytes();
        sendSize = String.valueOf(sendData.length).getBytes();

        socket.send(new DatagramPacket(sendSize, sendSize.length, receivePacket.getAddress(),
            receivePacket.getPort()));

        socket.send(new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
            receivePacket.getPort()));
      } else if ((receiveData.contains("modify")) && (Objects.equals(answerOnDate, "1"))) {
        socket.receive(receivePacket);
        stringWithIndexes = new String(receivePacket.getData(), 0, receivePacket.getLength());

        receivePacket = new DatagramPacket(new byte[200], 200);
        socket.receive(receivePacket);

        ORM.modify(new String(receivePacket.getData(), 0, receivePacket.getLength()),
                parsingIndexes(stringWithIndexes));
        getLastModificationDate();
      } else if ((receiveData.contains("remove")) && (Objects.equals(answerOnDate, "1"))) {
        sendData = "1".getBytes();
        socket.send(new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
            receivePacket.getPort()));

        socket.receive(receivePacket);
        stringWithIndexes = new String(receivePacket.getData(), 0, receivePacket.getLength());
        if (stringWithIndexes.length() != 0) {
          ORM.remove(parsingIndexes(stringWithIndexes));
          getLastModificationDate();
        }
      } else if ((receiveData.contains("add")) && (Objects.equals(answerOnDate, "1"))) {
        sendData = "1".getBytes();
        socket.send(new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
            receivePacket.getPort()));

        receivePacket = new DatagramPacket(new byte[200], 200);
        socket.receive(receivePacket);
        ORM.add(new String(receivePacket.getData(), 0, receivePacket.getLength()));
        getLastModificationDate();
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private static ArrayList<Integer> parsingIndexes(String string) throws Exception {
    ArrayList<Integer> searchedIndexes = new ArrayList<>();
    String arrayOfObjects = string.substring(0, string.length());

    for (String tokens : arrayOfObjects.split("%")) {
      searchedIndexes.add(Integer.parseInt(tokens));
    }
    return searchedIndexes;
  }

  private static void getLastModificationDate() {
    Date date =  new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    lastModified = formatter.format(date);
  }
}
