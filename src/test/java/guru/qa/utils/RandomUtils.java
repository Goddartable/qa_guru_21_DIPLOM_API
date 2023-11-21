package guru.qa.utils;

import com.github.javafaker.Faker;

import java.time.Instant;

public class RandomUtils {
    Faker myFaker = new Faker();

    public String userEmail = myFaker.internet().emailAddress();
    public String userName = myFaker.name().firstName();
    public String userJob = myFaker.job().position();
    public String userPassword = myFaker.internet().password();
    public String timeBeforeStartTest = Instant.now().toString();
}
