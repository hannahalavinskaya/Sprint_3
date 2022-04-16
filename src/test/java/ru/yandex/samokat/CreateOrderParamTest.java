package ru.yandex.samokat;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateOrderParamTest {

    public Order createOrder;
    private OrderClient orderClient;

    private Order order0;
    private Order order1;
    private Order order2;


    @Before
    public void setUp(){
        orderClient = new OrderClient();
        order0 = Order.getRandom(0);// создаем заказ со всеми обязательными полями без цвета
        order1 = Order.getRandom(1);// создаем заказ со всеми обязательными полями c 1 цветом
        order2 = Order.getRandom(2);// создаем заказ со всеми обязательными полями c 2 цветами
    }

    /*@After
    public void tearDown(){
        //?? не понятно как чистить все созданные заказы после каждого теста, в документации нет информации как удалить заказы после тестов
    }*/


    @Test
    @DisplayName("Check the status code of /api/v1/orders")
    @Description("Basic test for checking order creating with obligatory fields and no color of a scooter.")
    public void testSuccessOrderCreatingWithObligatoryFieldsAndNoColorOfScooter(){

        int trackNumber = orderClient.create(order0).assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("track");//проверка корректности выполнения первого запроса, создан ли заказ
        assertThat("Order is not created.", trackNumber, is(not(0)));
    }

    @Test
    @DisplayName("Check the status code of created order with one color.")
    @Description("Basic test for checking order creating with obligatory fields and one color of a scooter.")
    public void testSuccessOrderCreatingWithObligatoryFieldsAndOneColorOfScooter(){

        int trackNumber = orderClient.create(order1).assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("track");//проверка корректности выполнения первого запроса, создан ли заказ
        assertThat("Order is not created.", trackNumber, is(not(0)));
    }

    @Test
    @DisplayName("Check the status code of created order with two colors.")
    @Description("Basic test for checking order creating with obligatory fields and two colors of a scooter.")
    public void testSuccessOrderCreatingWithObligatoryFieldsAndTwoColorsOfScooter(){

        int trackNumber = orderClient.create(order2).assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("track");//проверка корректности выполнения первого запроса, создан ли заказ
        assertThat("Order is not created.", trackNumber, is(not(0)));
    }
}
