package jackson;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@MicronautTest
class JacksonTest {

    private static final String PAYLOAD = """
                {
                    "rootKey": {
                        "key1":"value1",
                        "key2": null
                    }                   
                }
                """;

    @Inject
    @Client("/endpoint")
    Rx3HttpClient client;

    @Test
    void mnDeserializtion() {
        var payload = HttpRequest.POST("/mnDeserialization", PAYLOAD);
        client.exchange(payload, Map.class).test().awaitDone(5, TimeUnit.SECONDS).assertNoErrors().assertValue(response -> {
            // DOES NOT WORK
            // Controller output:
            // ACTUAL: {rootKey={key1=value1}}
            // EXPECTED: {rootKey={key1=value1, key2=null}}
            return true;
        });
    }

    @Test
    void manualDeserialization() {
        var payload = HttpRequest.POST("/manualDeserialization", PAYLOAD);
        client.exchange(payload).test().awaitDone(5, TimeUnit.SECONDS).assertNoErrors().assertValue(response -> {
            // WORKS
            // Controller output
            // ACTUAL: {rootKey={key1=value1, key2=null}}
            // EXPECTED: {rootKey={key1=value1, key2=null}}
            return true;
        });
    }

}
