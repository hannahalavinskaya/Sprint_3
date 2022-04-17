package ru.yandex.samokat;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CourierCreationTest {

    public CourierClient courierClient;
    private int courierId;
    private Courier courier1;
    private Courier courier2;
    private Courier courier3;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier1 = new Courier("courier1","12345","courier1");// создаем  клиента
        courier2 = new Courier("courier1","12345","courier2");// создаем  клиента с тем же логином
        courier3 = new Courier("courier3","","courier2");// создаем без пароля
    }

    @After
    public void tearDown(){
        if (courierId !=0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Check the status code of /api/v1/courier") // имя теста
    @Description("Basic test for successful courier creation with the status code 201.") // описание теста
    public void testSuccessCourierCreating(){

        boolean isCreated = courierClient.create(courier1).assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");//проверка корректности выполнения первого запроса, создан ли клиент
        assertTrue("Courier is not created.", isCreated);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier1))
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id"); //если он создан, пробуем залогиниться под этим клиентом и из этого запроса берём Id
    }

    @Test
    @DisplayName("Check the status code with identical courier Conflict.")
    @Description("Basic test for for checking creating identical courier.")
    public void testNoPossibilityToCreateIdenticalCouriers(){

        courierClient.create(courier1);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier1))
                .extract()
                .path("id"); //если он создан, пробуем залогиниться под этим клиентом и из этого запроса берём Id

        courierClient.create(courier1).assertThat()
                .statusCode(SC_CONFLICT); //проверка, что не создался такой же курьер
    }

    @Test
    @DisplayName("Check the status code with Conflict.")
    @Description("Basic test for checking duplicate login request.")
    public void testNoPossibilityToCreateCourierWithTheSameLogin(){

        courierClient.create(courier1);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier1))
                .extract()
                .path("id"); //если он создан, пробуем залогиниться этим клиентом, и из этого запроса берём Id

        String message = courierClient.create(courier2).assertThat()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("message");//проверка корректности выполнения запроса, что клиент не создан
        assertEquals("Этот логин уже используется. Попробуйте другой.", message);
    }

    @Test
    @DisplayName("Check the status code with Bad Request.")
    @Description("Basic test for checking creating courier with incomplete data.")
    public void testNoPossibilityToCreateCourierWithIncompleteData(){

        String message = courierClient.create(courier3).assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");//проверка корректности выполнения запроса, что клиент не создан
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }
}
