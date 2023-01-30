package order;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderStep extends Client {
    static private String checkOrder = "/api/v1/orders";
    static private String createOrder = "/api/v1/orders";
    static private String cancelOrder = "/api/v1/orders/cancel";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given().log().all()
                .spec(getSpec())
                .body(order)
                .when()
                .post(createOrder)
                .then();
    }

    @Step("Удаление заказа")
    public ValidatableResponse delete(int track) {
        return given().log().all()
                .spec(getSpec())
                .body(track)
                .when()
                .put(cancelOrder)
                .then();
    }

    @Step("Список заказов")
    public ValidatableResponse checklist() {
        return given().log().all()
                .spec(getSpec())
                .when()
                .get(checkOrder)
                .then()
                .assertThat();
    }


}
