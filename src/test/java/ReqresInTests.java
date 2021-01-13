import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.is;


public class ReqresInTests {

    @BeforeEach
    void beforeEach() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    @DisplayName("Successful List Users Response Test")
    void successfulListUsersResponseTest() {
        given()
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.last_name", hasItemInArray("Howell"));
    }

    @Test
    @DisplayName("Successful Single User Response Test")
    void successfulSingleUserResponseTest() {
        given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg"));
    }

    @Test
    @DisplayName("Single user not found")
    void unsuccessfulSingleUserResponseTest() {
        given()
                .when()
                .get("users/23")
                .then()
                .statusCode(404);
    }

}
