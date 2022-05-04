import dto.LoginCourierDto;
import dto.RegisterCourierDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.CourierService;
import utils.DataGenerator;

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

        CourierService.register(newUser)
                .then().assertThat()
                .statusCode(201);
    }

    @After
    public void after() {
        int id = CourierService.login(courier)
                .then().assertThat()
                .statusCode(200)
                .extract().path("id");
        CourierService.delete(id)
                .then().assertThat()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    public void shouldCourierLogIn() {
        CourierService.login(courier)
                .then().assertThat()
                .statusCode(200)
                .body("id", any(Integer.class));
    }

    @Test
    public void shouldNotCourierLogInWithoutPass() {
        courier.setPassword(null);
        CourierService.login(courier)
                .then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void shouldNotCourierLogInWithoutLog() {
        courier.setLogin(null);
        CourierService.login(courier)
                .then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void shouldNotCourierLogInWithIncorrectPassword() {
        courier.setPassword(generateLoginCourierDto().getPassword());
        CourierService.login(courier)
                .then().assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void shouldNotCourierLogInWithIncorrectLogin() {
        courier.setLogin(generateLoginCourierDto().getLogin());
        CourierService.login(courier)
                .then().assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void shouldNotUnregisteredCourierLogIn() {
        CourierService.login(DataGenerator.generateLoginCourierDto())
                .then().assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

}
