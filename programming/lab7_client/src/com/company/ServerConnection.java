package com.company;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

class ServerConnection {
  static String sendCommand(String sendData, String sendObjectAfter, String sendObjectBefore) {
    try {
      DatagramChannel channel = DatagramChannel.open();
      ByteBuffer receiveSize = ByteBuffer.allocate(1024);
      ByteBuffer receiveData;
      String searchedString = "";

      channel.send(ByteBuffer.wrap(sendData.getBytes()), new InetSocketAddress("localhost", 9876));

      if (sendData.contains("load")) {
        channel.receive(receiveSize);
        receiveData = ByteBuffer.allocate(byteToInt(receiveSize));
        channel.receive(receiveData);
        searchedString = new String(receiveData.array());
      } else if (sendData.contains("modify")) {
        receiveData = ByteBuffer.allocate(1024);
        channel.receive(receiveData);
        if (byteToInt(receiveData) == 1) {
          searchedString = "1";
          channel.send(ByteBuffer.wrap(sendObjectAfter.getBytes()), new InetSocketAddress("localhost", 9876));
          channel.send(ByteBuffer.wrap(sendObjectBefore.getBytes()), new InetSocketAddress("localhost", 9876));
        } else {
          searchedString = "0";
        }
      } else {
      }

      return searchedString;
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  private static int byteToInt(ByteBuffer receiveSize) {
    int digit;
    StringBuilder resultString;
    resultString = new StringBuilder();

    for (char a : new String(receiveSize.array()).toCharArray()) {
      switch (a) {
        case 49:
          digit = 1;
          break;
        case 50:
          digit = 2;
          break;
        case 51:
          digit = 3;
          break;
        case 52:
          digit = 4;
          break;
        case 53:
          digit = 5;
          break;
        case 54:
          digit = 6;
          break;
        case 55:
          digit = 7;
          break;
        case 56:
          digit = 8;
          break;
        case 57:
          digit = 9;
          break;
        case 48:
          digit = 0;
          break;
        default:
          digit = 10;
      }
      if (digit == 10) {
        break;
      }
      resultString.append(digit);
    }
    return Integer.parseInt(resultString.toString());
  }
}
