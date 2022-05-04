package service;

import dto.CreateOrderDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderService {
    public static Response create(CreateOrderDto createOrderDto) {
        return given()
                .contentType(ContentType.JSON)
                .body(createOrderDto)
                .post("https://qa-scooter.praktikum-services.ru/api/v1/orders");
    }

    public static Response getList() {
        return given()
                .contentType(ContentType.JSON)
                .get("https://qa-scooter.praktikum-services.ru/api/v1/orders");
    }
}
