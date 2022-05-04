import dto.RegisterCourierDto;
import org.junit.Test;
import service.CourierService;
import utils.DataGenerator;

import static org.hamcrest.Matchers.equalTo;

public class CreateCouriersTest {

    @Test
    public void shouldCreateCourier() {
        CourierService.register(DataGenerator.createCourier())
                .then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void shouldNotCreateSameCourier() {
        RegisterCourierDto courierDto = DataGenerator.createCourier();

        CourierService.register(courierDto)
                .then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        CourierService.register(courierDto)
                .then().assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void shouldWithoutNameCreateCourier() {
        RegisterCourierDto courierDto = DataGenerator.createCourier();
        courierDto.setFirstName(null);
        CourierService.register(courierDto)
                .then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void shouldWithoutPasswordCreateCourier() {
        RegisterCourierDto courierDto = DataGenerator.createCourier();
        courierDto.setPassword(null);
        CourierService.register(courierDto)
                .then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void shouldWithoutLoginCreateCourier() {
        RegisterCourierDto courierDto = DataGenerator.createCourier();
        courierDto.setLogin(null);
        CourierService.register(courierDto)
                .then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
