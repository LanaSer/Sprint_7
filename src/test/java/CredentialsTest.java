import courier.Courier;
import courier.CourierStep;
import courier.CourierGenerator;
import courier.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CredentialsTest {
    private Courier courier;
    private Credentials credentials;
    private CourierStep courierClient;
    private String id;

    @Before
    public void setUp() {
        courier = CourierGenerator.random();
        courierClient = new CourierStep();
    }

    @After
    public void cleanUp() {
        if (id != null) {
            courierClient.delete(id);
        }
    }

    @Test
    @DisplayName("Тестирование авторизации курьера")
    public void loginCourierTest() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginresponse = courierClient.login(Credentials.from(courier));
        id = loginresponse.extract().path("id").toString();
        response.assertThat().statusCode(201).body("ok", is(true));
    }

    @Test
    @DisplayName("Тестирование авторизации курьера без логина")
    public void withoutLoginCourierTest() {
        courier.setLogin("");
        ValidatableResponse response = courierClient.login(Credentials.from(courier));
        response.statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тестирование авторизации курьера без логина")
    public void withoutPasswordCourierTest() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.login(Credentials.from(courier));
        response.statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}





