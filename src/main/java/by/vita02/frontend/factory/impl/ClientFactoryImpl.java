package by.vita02.frontend.factory.impl;

import by.vita02.frontend.POJOs.Client;
import by.vita02.frontend.factory.ClientFactory;
import com.google.gson.JsonObject;

public class ClientFactoryImpl implements ClientFactory {
  @Override
  public Client createClientFromJsonObject(JsonObject jsonObject) {
    Client client =
        new Client(
            jsonObject.get("id").getAsLong(),
            jsonObject.getAsJsonObject().get("nickName").getAsString(),
            jsonObject.getAsJsonObject().get("passportNumber").getAsString(),
            jsonObject.getAsJsonObject().get("name").getAsString(),
            jsonObject.getAsJsonObject().get("surname").getAsString(),
            jsonObject.getAsJsonObject().get("emailAddr").getAsString());
    return client;
  }
}
