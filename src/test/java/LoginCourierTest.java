import dto.LoginCourierDto;
import dto.RegisterCourierDto;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.CourierService;
import utils.DataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static utils.DataGenerator.generateLoginCourierDto;

public class LoginCourierTest {
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
    @DisplayName("Проверка возможности авторизации")
    public void shouldCourierLogIn() {
        CourierService.loginCourier(courier)
                .then().assertThat()
                .statusCode(SC_OK)
                .body("id", any(Integer.class));
    }

    @Test
    @DisplayName("Проверка валидации при авторизации без пароля")
    public void shouldNotCourierLogInWithoutPass() {
        courier.setPassword(null);
        CourierService.loginCourier(courier)
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка валидации при авторизации без логина")
    public void shouldNotCourierLogInWithoutLog() {
        courier.setLogin(null);
        CourierService.loginCourier(courier)
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка невозможности авторизации по неверному паролю")
    public void shouldNotCourierLogInWithIncorrectPassword() {
        courier.setPassword(generateLoginCourierDto().getPassword());
        CourierService.loginCourier(courier)
                .then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка невозможности авторизации по неверному логину")
    public void shouldNotCourierLogInWithIncorrectLogin() {
        courier.setLogin(generateLoginCourierDto().getLogin());
        CourierService.loginCourier(courier)
                .then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка невозможности авторизации несуществующим пользователем")
    public void shouldNotUnregisteredCourierLogIn() {
        CourierService.loginCourier(DataGenerator.generateLoginCourierDto())
                .then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
