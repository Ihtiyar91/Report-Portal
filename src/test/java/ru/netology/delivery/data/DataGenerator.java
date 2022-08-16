package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.experimental.UtilityClass;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@UtilityClass
public class DataGenerator {

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    }
    public static String generateValidCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String city = (faker.options().option("Москва", "Санкт-Петербург", "Ульяновск", "Кострома", "Липецк", "Казань", "Самара", "Саранск", "Чебоксары", "Краснодар", "Екатеринбург"));
        return city;
    }

    public static String generateValidName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName().replaceAll("[^А-Е-Ж-Яа-е-ж-я]","");
        return name;
    }
    public static String generateInvalidPhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.numerify("+79#####");
        return phone;
    }

    public static String generateInvalidCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String city = faker.address().city();
        return city;
    }

    public static String generateInvalidName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        return name;
    }

    @UtilityClass
    public static class Registration {
        public static UserInfo validUser(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(generateValidCity(locale),
                    generateValidName(locale),
                    faker.phoneNumber().phoneNumber());

        }

        public static UserInfo nameYoRegistration(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(generateValidCity(locale),
                    faker.name().fullName().concat("ё"),
                    faker.phoneNumber().phoneNumber());
        }

        public static UserInfo invalidPhoneRegistration(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(generateValidCity(locale),
                    generateValidName(locale),
                    generateInvalidPhone(locale));

        }

        public static UserInfo invalidCityRegistration(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(generateInvalidCity("en"),
                    generateValidName(locale),
                    faker.phoneNumber().phoneNumber());

        }

        public static UserInfo invalidNameRegistration(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(generateValidCity(locale),
                    generateInvalidName("en"),
                    faker.phoneNumber().phoneNumber());
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
