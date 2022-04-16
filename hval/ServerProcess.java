package hval;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import julya.pool.Process;
import julya.utils.FileUtils;

public class ServerProcess implements Process<Socket> {
  private BufferedReader in;
  private PrintWriter out;

  public void process(Socket socket) {
    String fileName;
    openStreams(socket);
    fileName = getRequestedFile(getRequest());
    sendResponse(fileName, getContent(fileName));
    closeStreams();
    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void closeStreams() {
    try {
      in.close();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getContent(String fileName) {
    String fileContent;
    if (fileName != null) {
      fileName = Hval.HOME + fileName;
    }
    if ((fileContent = FileUtils.readFile(fileName)) == null) {
      fileContent = "<h1>404 Not Found<h1>";
    }
    return fileContent;
  }

  private String getContentType(String fileName) {
    switch (FileUtils.getFileExtension(fileName)) {
      case "html":
      case "htm":
        return "text/html";
      case "jpg":
      case "jpeg":
        return "image/jpeg";
      case "png":
        return "image/png";
      case "gif":
        return "image/gif";
      case "css":
        return "text/css";
      case "js":
        return "application/javascript";
      case "pdf":
        return "application/pdf";
      default:
        return "text/plain";
    }
  }

  private String getRequest() {
    StringBuilder request;
    String line;
    request = new StringBuilder();
    try {
      do {
        line = in.readLine();
        if (line != null) {
          request.append(line);
        }
      } while (line != null && !line.equals(""));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return request.toString();
  }

  private String getRequestedFile(String request) {
    String fileName;
    if (request.startsWith("GET") && request.indexOf("HTTP") != -1) {
      fileName = request.substring(request.indexOf("/"), request.indexOf("HTTP") - 1);
      if (fileName.endsWith("/")) {
        fileName += "index.html";
      }
    } else {
      fileName = null;
    }
    return fileName;
  }

  private void openStreams(Socket socket) {
    try {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void sendResponse(String fileName, String content) {
    StringBuilder responseBuilder;
    responseBuilder = new StringBuilder();
    responseBuilder.append("HTTP/1.1 200 OK\n");
    responseBuilder.append("Content-Type: " + FileUtils.getFileExtension(fileName) + "\n");
    responseBuilder.append("Content-Length: " + content.length() + "\n");
    responseBuilder.append("\n");
    responseBuilder.append(content);
    out.print(responseBuilder.toString());
    out.flush();
  }
}
