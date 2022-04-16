package hval;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import julya.io.CLI;
import julya.pool.ProcessPool;

public class Server extends Thread {
  private ProcessPool<Socket> pool;
  private int port;
  private boolean running;

  public Server(int port, int poolSize) {
    this.pool = new ProcessPool<Socket>(poolSize, new ServerProcess());
    this.port = port;
  }

  public void run() {
    ServerSocket serverSocket;
    Socket socket;
    this.pool.start();
    this.running = true;
    try {
      serverSocket = new ServerSocket(this.port);
      CLI.printInfo("Server started");
      while (this.running) {
        socket = serverSocket.accept();
        CLI.printInfo("Connection from " + socket.getInetAddress().getHostName());
        this.pool.addToQueue(socket);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.pool.shutdown();
    CLI.printInfo("Server shutdown");
  }

  public void shutdown() {
    this.running = false;
  }
}
