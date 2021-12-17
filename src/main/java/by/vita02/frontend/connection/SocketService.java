package by.vita02.frontend.connection;

import java.io.*;
import java.net.Socket;

public class SocketService {
  private static Socket socket;
  private static BufferedWriter bufferedWriter;
  private static BufferedReader bufferedReader;

  public static void init() {
    try {
      socket = new Socket("localhost", 8080);
      bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      bufferedWriter.flush();
      bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (Exception e) {
      System.out.println("Server is offline");
    }
  }

  public static void close() {
    try {
      socket.close();
      bufferedWriter.close();
      bufferedReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static BufferedWriter getBufferedWriter() {
    return bufferedWriter;
  }

  public static BufferedReader getBufferedReader() {
    return bufferedReader;
  }

  public static void writeLine(String line) throws IOException {
    bufferedWriter.write(line + "\n");
    bufferedWriter.flush();
  }

  public static String readLine() throws IOException {
    return bufferedReader.readLine();
  }
}
