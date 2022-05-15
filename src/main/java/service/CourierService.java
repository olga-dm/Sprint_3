package service;

import dto.LoginCourierDto;
import dto.RegisterCourierDto;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static service.RestAssured.getBaseSpec;

public class CourierService {
    private static final String COURIER_PATH = "api/v1/courier/";

    @Step
    public static Response registerCourier(RegisterCourierDto registerCourierDto) {
        return given()
                .spec(getBaseSpec())
                .contentType(ContentType.JSON)
                .body(registerCourierDto)
                .post(COURIER_PATH);
    }

    @Step
    public static Response loginCourier(LoginCourierDto loginCourierDto) {
        return given()
                .spec(getBaseSpec())
                .contentType(ContentType.JSON)
                .body(loginCourierDto)
                .post(COURIER_PATH + "login");
    }

    @Step
    public static Response deleteCourier(int id) {
        return given()
                .spec(getBaseSpec())
                .contentType(ContentType.JSON)
                .body(Map.of("id", id))
                .delete(COURIER_PATH + id);
    }
}
