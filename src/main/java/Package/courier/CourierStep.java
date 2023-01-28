package Package.courier;

import Package.client.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CourierStep extends Client {
    protected final CourierGenerator generator = new CourierGenerator();
    private static final String CREATE_COURIER = "/api/v1/courier";
    private static final String LOGIN_COURIER = "api/v1/courier/login";
    private static final String DELETE_COURIER = "api/v1/courier/";

    @Step("Создание курьра")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .when()
                .body(courier)
                .post(CREATE_COURIER)
                .then().log().all();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec((RequestSpecification) getSpec())
                .when()
                .body(credentials)
                .post(LOGIN_COURIER)
                .then().log().all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(String id) {
        return given()
                .spec((RequestSpecification) getSpec())
                .when()
                .delete(DELETE_COURIER + id)
                .then();
    }
}


