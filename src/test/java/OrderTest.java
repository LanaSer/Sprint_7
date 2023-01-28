
import Package.order.Order;
import Package.order.OrderStep;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {
    private OrderStep orderStep;
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

    public OrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] orderColor() {
        return new Object[][]{
                {new String[]{}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
        };
    }

    @Before
    public void setUp() {
        orderStep = new OrderStep();
    }

    @Test
    @DisplayName("Тестируем цвет самакатов")
    public void orderTest() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = orderStep.create(order);
        response.assertThat().log().all().statusCode(201).body("track", is(notNullValue()));
        track = response.extract().path("track");
    }

    @After
    public void tearDown() {
        ValidatableResponse response = orderStep.delete(track);

    }
}