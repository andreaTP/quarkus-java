package com.github.andreatp.quarkus.kiota.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microsoft.kiota.RequestAdapter;
import com.microsoft.kiota.authentication.AnonymousAuthenticationProvider;
import com.microsoft.kiota.http.VertXRequestAdapter;
import foo.bar.MyApiClient;
import foo.bar.models.Greeting;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class QuarkusKiotaResourceTest {

    @Test
    public void testHelloEndpoint() {
        given().when()
                .get("/quarkus-kiota")
                .then()
                .statusCode(200)
                .body(is("{\"value\":\"Hello quarkus-kiota\"}"));
    }

    @Test
    public void testHelloEndpointUsingTheKiotaClient() throws Exception {
        // Arrange
        RequestAdapter adapter = new VertXRequestAdapter(new AnonymousAuthenticationProvider());
        adapter.setBaseUrl("http://localhost:8081");
        MyApiClient client = new MyApiClient(adapter);

        // Act
        Greeting result = client.quarkusKiota().get();

        // Assert
        assertEquals("Hello quarkus-kiota", result.getValue());
    }
}
