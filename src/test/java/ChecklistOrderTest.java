
import order.OrderStep;
import order.Order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class ChecklistOrderTest {
    private OrderStep orderStep;

    @Before
    public void setUp() {
        orderStep = new OrderStep();
    }

    @Test
    @DisplayName("Проверяем список заказов")
    public void checklistOrderTest() {
        ValidatableResponse response = orderStep.checklist();
        response.statusCode(200).and().body("orders", notNullValue());
    }
}
