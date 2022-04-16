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
import static org.junit.Assert.*;

public class CourierLoginTest {
    public CourierClient courierClient;
    private Courier courier1;


    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier1 = new Courier("courier1","12345","courier1");// создаем  клиента
        courierClient.create(courier1);
    }

    @After
    public void tearDown(){
        courierClient.delete(courierClient.login(CourierCredentials.getCourierCredentials(courier1))
                .extract()
                .path("id"));
    }

    @Test
    @DisplayName("Check the status code of /api/v1/courier/login")
    @Description("Basic test for successful courier login with the status code 200.")
    public void testCourierIsLoggedIn(){

        int courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier1))
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id"); // пробуем залогиниться под этим клиентом и из этого запроса берём Id
        assertThat("Courier ID is incorrect.", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Check the status code with request with incorrect login/password.")
    @Description("Basic test for checking login with incorrect login/password.")
    public void testSystemReturnsErrorIfUsernameOrPasswordIsIncorrect(){
        String message = courierClient.login(new CourierCredentials("courier1", "1234"))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");
        assertEquals("Courier is logged.", message, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Check the status code with an incomplete data.")
    @Description("Basic test for request without login or password.")
    public void testLoginWithIncompleteDataReturnsAnError(){
        String message = courierClient.login(new CourierCredentials("courier1", ""))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");
        assertEquals("Courier is logged.", message, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Check the status code with request with a non-existent login-password pair.")
    @Description("Basic test for checking login under non-existent user.")
    public void testLoginUnderNonExistentUserTheRequestReturnsAnError(){
        String message = courierClient.login(new CourierCredentials("courier4", "7777"))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");
        assertEquals("Courier is logged.", message, "Учетная запись не найдена");
    }
}
