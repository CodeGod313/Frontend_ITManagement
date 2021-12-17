package test;

import by.vita02.frontend.POJOs.Order;
import by.vita02.frontend.builder.OrderBuilder;
import by.vita02.frontend.builder.impl.OrderBuilderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderBuilderImplTest {

    OrderBuilder orderBuilder;

    @BeforeAll
    public void setUp(){
        orderBuilder = new OrderBuilderImpl();
        orderBuilder.setDate("11 September");
        orderBuilder.setProjectType("MOBILE_APP");
        orderBuilder.setId(11L);
        orderBuilder.setStatus("accepted");
        orderBuilder.setCost(111);
        orderBuilder.setNumOfConvUnits(1);
        orderBuilder.setCompanyName("CleverDeath Inc.");
        orderBuilder.setIsPayed("true");
    }

    @Test
    void build() {
        Order expected = new Order(11L, "MOBILE_APP", "11 September", "accepted", 111, 1, "CleverDeath Inc.", "true");
        Order actual = orderBuilder.build();
        Assertions.assertEquals(expected, actual);
    }
}