package service;

import dto.CreateOrderDto;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static service.RestAssured.getBaseSpec;

public class OrderService{
    private static final String ORDER_PATH = "api/v1/orders/";

    @Step
    public static Response createOrder(CreateOrderDto createOrderDto) {
        return given()
                .spec(getBaseSpec())
                .contentType(ContentType.JSON)
                .body(createOrderDto)
                .post(ORDER_PATH);
    }

    @Step
    public static Response getListOrders() {
        return given()
                .spec(getBaseSpec())
                .contentType(ContentType.JSON)
                .get(ORDER_PATH);
    }
}
