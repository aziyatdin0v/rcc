package io.student.rcc.utils;

import com.github.javafaker.Faker;

public class DataGenerator {

    private static final Faker faker = new Faker();

    public static String generateLogin() {
        return faker.name().username();
    }

    public static String generatePassword() {
        return faker.internet().password(4, 8);
    }
}
