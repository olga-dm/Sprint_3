import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import service.OrderService;
import utils.DataGenerator;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(JUnitParamsRunner.class)
public class CreateOrderTest {

    @Parameters(method = "getColorParams")
    @Test
    public void shouldCreateOrderWithVariousColors(List<String> color) {
        var dto = DataGenerator.generateCreateOrderDto();
        dto.setColor(color);
        OrderService.create(dto)
                .then().assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }

    public static List<List<String>> getColorParams() {
        return List.of(
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                List.of()
        );
    }
}
