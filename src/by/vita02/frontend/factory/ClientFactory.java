package by.vita02.frontend.factory;

import by.vita02.frontend.POJOs.Client;
import com.google.gson.JsonObject;

public interface ClientFactory {
    Client createClientFromJsonObject(JsonObject jsonObject);
}
