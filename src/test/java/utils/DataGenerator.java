package utils;

import com.github.javafaker.Faker;
import dto.CreateOrderDto;
import dto.LoginCourierDto;
import dto.RegisterCourierDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextBoolean;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class DataGenerator {
    static Faker faker = new Faker();

    public static RegisterCourierDto createCourier() {
        String firstName = faker.name().firstName();
        String login = faker.name().username();
        String password = faker.internet().password();
        return new RegisterCourierDto(login, password, firstName);
    }

    public static LoginCourierDto generateLoginCourierDto(){
        String login = faker.name().username();
        String password = faker.internet().password();
        return new LoginCourierDto(login,password);
    }

    public static CreateOrderDto generateCreateOrderDto() {
        var dto = new CreateOrderDto();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address= faker.address().fullAddress();
        String metroStation = faker.number().toString();
        String phone = faker.numerify("+7 ### ### ## ##");
        String rentTime = faker.number().toString();
        String deliveryDate = (LocalDate.now().plusDays(nextInt(1, 5)).format(DateTimeFormatter.ISO_LOCAL_DATE));
        String comment =faker.animal().name();
        dto.getColor().add("BLACK");
        dto.getColor().add("GREY");
        return dto;
    }

}
