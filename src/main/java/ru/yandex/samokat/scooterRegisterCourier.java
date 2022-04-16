package ru.yandex.samokat;

import io.restassured.RestAssured; // импортируем RestAssured
import io.restassured.response.Response; // импортируем Response
import org.apache.commons.lang3.RandomStringUtils; // импортируем библиотеку генерации строк
import java.util.ArrayList; // импортируем список
import static io.restassured.RestAssured.*; // дополнительный статический импорт нужен, чтобы использовать given(), get() и then()


public class scooterRegisterCourier {

    /*
    метод регистрации нового курьера, возвращает список из логина и пароля
    если регистрация не удалась, возвращает пустой список
    */
    public ArrayList<String> registerNewCourierAndReturnLoginPassword(){

        // с помощью библиотеки RandomStringUtils генерируем логин
        // метод randomAlphabetic генерирует строку, состоящую только из букв, в качестве параметра передаём длину строки
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя курьера
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        // создаём список, чтобы метод мог его вернуть
        ArrayList<String> loginPass = new ArrayList<>();

        // собираем в строку тело запроса на регистрацию, подставляя в него логин, пароль и имя курьера
        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";

        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier");

        // если регистрация прошла успешно (код ответа 201), добавляем в список логин и пароль курьера
        if (response.statusCode() == 201) {
            loginPass.add(courierLogin);
            loginPass.add(courierPassword);
        }

        return loginPass; // возвращаем список
    }
}
