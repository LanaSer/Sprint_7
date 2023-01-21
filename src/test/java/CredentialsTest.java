import Package.Courier;
import Package.CourierClient;
import Package.CourierGenerator;
import Package.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
public class CredentialsTest {
    private  Courier courier;
    private Credentials credentials;
    private CourierClient courierClient;
    private String id;
    @Before
    public void setUp() {
        courier = CourierGenerator.random();
        courierClient = new CourierClient();
    }
    @After
    public void cleanUp(){
        if (id != null) {
            courierClient.delete(id);
        }
    }
    @Test
    @DisplayName("Тестирование авторизации курьера")
    public void LoginCourierTest(){
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginresponse = courierClient.login(Credentials.from(courier));
        id = loginresponse.extract().path("id").toString();
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }
    @Test
    @DisplayName("Тестирование авторизации курьера без логина")
    public void WithoutLoginCourierTest() {
        courier.setLogin("");
        ValidatableResponse response = courierClient.login(Credentials.from(courier));
        response.assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }
    @Test
    @DisplayName("Тестирование авторизации курьера без логина")
    public void WithoutPasswordCourierTest() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.login(Credentials.from(courier));
        response.assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(400);
    }
}





