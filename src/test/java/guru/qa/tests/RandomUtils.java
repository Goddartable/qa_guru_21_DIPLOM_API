package guru.qa.tests;

import com.github.javafaker.Faker;

import java.time.Instant;

public class RandomUtils {
    Faker myFaker = new Faker();

    String userEmail = myFaker.internet().emailAddress(),
            userName = myFaker.name().firstName(),
            userJob = myFaker.job().position(),
            userPassword = myFaker.internet().password(),
            timeBeforeStartTest = Instant.now().toString();
}
