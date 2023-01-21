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
public class CourierTest {

        private  Courier courier;
        private  CourierClient courierClient;
        private String id;
        @Before
        public void setUp(){
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
        @DisplayName("Тестирование создания курьера")
        public void CreatureCourierTest() {
            ValidatableResponse response = courierClient.create(courier);
            response.statusCode(201);
            ValidatableResponse loginresponse = courierClient.login(Credentials.from(courier));
            id = loginresponse.extract().path("id").toString();
        }
        @Test
        @DisplayName("Тестирование создания курьера без логина")
        public void CreatureCourierWithoutLoginTest() {
            courier.setLogin("");
            ValidatableResponse response = courierClient.create(courier);
            response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                    .and().statusCode(400);
        }
        @Test
        @DisplayName("Тестирование создания курьера без пароля")
        public void CreatureCourierWithoutPasswordTest(){
            courier.setPassword("");
            ValidatableResponse response = courierClient.create(courier);
            response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
        }
        @Test
        @DisplayName("Тестирование создания курьера с одинаковым логином ")
        public void CreatureCourierDoubleTest() {
            courierClient.create(courier);
            ValidatableResponse response = courierClient.create(courier);
            response.assertThat().body("message", equalTo("Этот логин уже используется"))
                    .and().statusCode(409);
        }


}
