package service;

import dto.LoginCourierDto;
import dto.RegisterCourierDto;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import io.restassured.response.Validatable;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CourierService {

    public static Response register(RegisterCourierDto registerCourierDto) {
        return given()
                .contentType(ContentType.JSON)
                .body(registerCourierDto)
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier");
    }
    public static Response login(LoginCourierDto loginCourierDto) {
        return given()
                .contentType(ContentType.JSON)
                .body(loginCourierDto)
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier/login");
    }

    public static Response delete(int id) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of("id", id))
                .delete("https://qa-scooter.praktikum-services.ru/api/v1/courier/{id}", id);
    }

}
