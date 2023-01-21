import Package.Client;
import Package.Order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest extends Client {
    static private String createOrder = "/api/v1/orders";
    static private String cancelOrder = "/api/v1/orders/cancel";
    int track;
    private String firstName = "Ichigo";
    private String lastName = "Kurosaki";
    private String address = "Karakura, ";
    private String metroStation = "5";
    private String phone = "8900000000";
    private int rentTime = 3;
    private String deliveryDate = "2022.12.31";
    private String comment = "Bankai";
    private String[] color;
    public OrderTest(String[]color) {
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] orderColor() {
        return new Object[][]{
                { new String[] {}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
        };
    }
    @Test
    @DisplayName("Тестируем цвет самакатов")
    public void orderTest(){
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(createOrder);
        response.then().log().all()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
        track = response.path("track");
    }
    @After
    public ValidatableResponse tearDown(int track) {
        return given().log().all()
                .spec(getSpec())
                .body(track)
                .when()
                .put(cancelOrder)
                .then();
    }
}