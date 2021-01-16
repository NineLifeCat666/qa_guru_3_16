import com.sun.istack.NotNull;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ReqresInTests {

    @BeforeEach
    void beforeEach() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    @DisplayName("Successful List Users Response")
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
    @DisplayName("Successful Single User Response")
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
                .statusCode(404)
                .log().body()
                .body("token", nullValue());
    }

    @Test
    @DisplayName("Successful registration")
    void successfulRegistrationTest() {
        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .post("/register")
                .then()
                .statusCode(200)
                .log().body()
                .body("token", notNullValue());

    }

    @Test
    @DisplayName("Successful login")
    void successfulLoginTest() {
        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .post("/login")
                .then()
                .statusCode(200)
                .log().body()
                .body("token", notNullValue());
    }

}
