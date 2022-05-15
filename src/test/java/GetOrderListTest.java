import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import service.OrderService;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.isA;

public class GetOrderListTest {

    @Test
    @DisplayName("Проверка возвращаемого списка заказов")
    public void shouldReturnNotNullOrdersList() {
        OrderService.getListOrders()
                .then().assertThat()
                .statusCode(SC_OK)
                .body("orders", isA(List.class));
    }
}
