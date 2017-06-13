package com.company;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Main {
  public static void main(String args[]) {
    try {
      CollectionInterface.createInterface();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
}