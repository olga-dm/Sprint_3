import io.qameta.allure.junit4.DisplayName;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import service.OrderService;
import utils.DataGenerator;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(JUnitParamsRunner.class)
public class CreateOrderTest {

    public static List<List<String>> getColorParams() {
        return List.of(
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                List.of()
        );
    }

    @Parameters(method = "getColorParams")
    @Test
    @DisplayName("Проверка создания заказа с доступными цветами")
    public void shouldCreateOrderWithVariousColors(List<String> color) {
        var dto = DataGenerator.generateCreateOrderDto();
        dto.setColor(color);
        OrderService.createOrder(dto)
                .then().assertThat()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}
