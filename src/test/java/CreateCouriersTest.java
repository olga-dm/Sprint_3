import dto.LoginCourierDto;
import dto.RegisterCourierDto;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.CourierService;
import utils.DataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateCouriersTest {
    LoginCourierDto courier;

    @Before
    public void before() {
        RegisterCourierDto newUser = DataGenerator.createCourier();
        courier = new LoginCourierDto();
        courier.setLogin(newUser.getLogin());
        courier.setPassword(newUser.getPassword());

        CourierService.registerCourier(newUser)
                .then().assertThat()
                .statusCode(SC_CREATED);
    }

    @After
    public void tearDown() {
        int id = CourierService.loginCourier(courier)
                .then().assertThat()
                .statusCode(SC_OK)
                .extract().path("id");
        CourierService.deleteCourier(id)
                .then().assertThat()
                .statusCode(SC_OK)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка cоздания курьера")
    public void shouldCreateCourier() {
        CourierService.registerCourier(DataGenerator.createCourier())
                .then().assertThat()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка валидации при создании курьера с ранее использованным логином")
    public void shouldNotCreateSameCourier() {
        RegisterCourierDto courierDto = DataGenerator.createCourier();
        CourierService.registerCourier(courierDto)
                .then().assertThat()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));

        CourierService.registerCourier(courierDto)
                .then().assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Проверка создания курьера без имени")
    public void shouldWithoutNameCreateCourier() {
        RegisterCourierDto courierDto = DataGenerator.createCourier();
        courierDto.setFirstName(null);
        CourierService.registerCourier(courierDto)
                .then().assertThat()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка валидации при создании курьера без пароля")
    public void shouldWithoutPasswordCreateCourier() {
        RegisterCourierDto courierDto = DataGenerator.createCourier();
        courierDto.setPassword(null);
        CourierService.registerCourier(courierDto)
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка валидации при создании курьера без логина")
    public void shouldWithoutLoginCreateCourier() {
        RegisterCourierDto courierDto = DataGenerator.createCourier();
        courierDto.setLogin(null);
        CourierService.registerCourier(courierDto)
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
