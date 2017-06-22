package com.company;

import java.net.DatagramSocket;

public class Main {
  public static void main(String args[]) {
    try {
      ClientConnection.socket = new DatagramSocket(9876);
      ORM.connection = DataBaseController.connection();


      while (true) {
        ClientConnection.waitingCommand();
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
}