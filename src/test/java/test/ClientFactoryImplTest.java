package test;

import by.vita02.frontend.POJOs.Client;
import by.vita02.frontend.factory.ClientFactory;
import by.vita02.frontend.factory.impl.ClientFactoryImpl;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientFactoryImplTest {

    ClientFactory clientFactory;
    JsonObject jsonObject;

    @BeforeAll
    public void setUp(){
        clientFactory = new ClientFactoryImpl();
        jsonObject = new JsonObject();
        jsonObject.addProperty("id", 111L);
        jsonObject.addProperty("nickName", "CodeGod313");
        jsonObject.addProperty("passportNumber", "MC2810513");
        jsonObject.addProperty("name", "Ales");
        jsonObject.addProperty("surname", "Shpak");
        jsonObject.addProperty("emailAddr", "codegod313@gmail.com");


    }

    @Test
    void createClientFromJsonObject() {
        Client expected = new Client(111L, "CodeGod313", "MC2810513", "Ales", "Shpak", "codegod313@gmail.com");
        Client actual = clientFactory.createClientFromJsonObject(jsonObject);
        Assertions.assertEquals(expected, actual);
    }
}