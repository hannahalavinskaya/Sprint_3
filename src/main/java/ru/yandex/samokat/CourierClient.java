package ru.yandex.samokat;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierClient extends RestAssuredClient{

    private static final String COURIER_PATH = "/api/v1/courier/";

    @Step("Creating a courier")
    public ValidatableResponse create(Courier courier){
        return given()
                .log().all()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Courier login")
    public ValidatableResponse login(CourierCredentials courierCredentials){
        return given()
                .log().all()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH+"login")
                .then();
    }

    @Step("Deleting a courier")
    public boolean delete(int courierId){
        return given()
                .log().all()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }
}
