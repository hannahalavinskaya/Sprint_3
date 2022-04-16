package ru.yandex.samokat;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class OrderListTest {
    private OrderClient orderClient;

    private Order order0;
    private Order order1;
    private Order order2;
    private int[] order_ids;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
        orderClient.create(Order.getRandom(0));
        orderClient.create(Order.getRandom(1));
        orderClient.create(Order.getRandom(2));
    }
    /*@After   // не понятно как чистить все созданные заказы после каждого теста, эта часть без чистки не работает
    public void tearDown(){
        for(int i = 0; i < order_ids.length; i++) {
            System.out.print(order_ids[i]);
            System.out.print(" ");
            orderClient.delete(order_ids[i]);
        }
    }*/

    @Test
    @DisplayName("Check the status code of /api/v1/orders. Get list of orders") // имя теста
    @Description("Basic test for getting a list of orders.") // описание теста
    public void testGetOrderList() {
        ResponseBodyExtractionOptions body = orderClient.getList().extract().body();
        ArrayList<Order> dat = body.path("orders"); // получение списка заказов из запроса

        assertThat("Orders count is incorrect.", dat.size(), is(not (0)));
    }
}
