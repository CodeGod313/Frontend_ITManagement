package by.vita02.frontend.services;

import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.QueryDTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.source.tree.Scope;

public class SignInService {
  public boolean signIn(String nickName, String password) {
    try {
      Gson gson = new Gson();
      QueryDTO queryDTO = new QueryDTO((long) -1, "signIn");
      SocketService.writeLine(gson.toJson(queryDTO));
      SocketService.writeLine(nickName);
      SocketService.writeLine(password);
      String answer = SocketService.getBufferedReader().readLine();
      if (answer.equals("refuse")) {
        return false;
      }
      JsonObject jsonObject = gson.fromJson(answer, JsonObject.class);
      LastQueryService.setUserId(jsonObject.get("id").getAsLong());
    } catch (Exception e) {
      System.out.println("Error: server disconnected");
    }
    return true;
  }
}
