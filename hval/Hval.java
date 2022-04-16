package hval;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Hval {
  public static String HOME;

  public static void main(String[] args) {
    Server server;
    Hval.HOME = (args.length > 0) ? args[0].endsWith("/") ? args[0].substring(0, args[0].length() - 2) : args[0] : ".";
    server = new Server(8080, 3);
    server.start();
  }
}
