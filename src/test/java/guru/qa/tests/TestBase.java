package guru.qa.tests;

import org.junit.jupiter.api.BeforeAll;
import io.restassured.RestAssured;

public class TestBase {
    RandomUtils randomUtils = new RandomUtils();

    @BeforeAll
    static void settingURI() {
        RestAssured.baseURI = "https://reqres.in";
    }
}
