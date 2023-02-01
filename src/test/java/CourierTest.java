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

public class CourierTest {

    private Courier courier;
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
    @DisplayName("Тестирование создания курьера")
    public void creatureCourierTest() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginresponse = courierClient.login(Credentials.from(courier));
        id = loginresponse.extract().path("id").toString();
        response.assertThat().statusCode(201).body("ok", is(true));
    }

    @Test
    @DisplayName("Тестирование создания курьера без логина")
    public void creatureCourierWithoutLoginTest() {
        courier.setLogin("");
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тестирование создания курьера без пароля")
    public void creatureCourierWithoutPasswordTest() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тестирование создания курьера с одинаковым логином ")
    public void creatureCourierDoubleTest() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(409).and().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой"));
    }
}
