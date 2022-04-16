package ru.yandex.samokat;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;


public class OrderClient extends RestAssuredClient{
    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step()
    public ValidatableResponse create(Order order){
        return given()
                .log().all()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step()
    public boolean delete(int orderId) {
        return given()
                .log().all()
                .spec(getBaseSpec())
                .when()
                .delete(ORDER_PATH + orderId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }

    @Step()
    public ValidatableResponse getList(){
        return given()
                .log().all()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }
}
