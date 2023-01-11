package jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@MicronautTest
class JacksonTest {

    private static final Logger log = LogManager.getLogger();

    //Map<String, Map<String, MyDto>>
    private String PAYLOAD = """
            {
                "firstKey": {
                    "secondKey": null,
                    "thirdKey": {
                        "name": "Jackson"
                    }
                }
            }
            """;

    @Inject
    @Client("/endpoint")
    Rx3HttpClient client;

    @Inject
    ObjectMapper mapper;

    /*
    * Micronaut client logs are set to TRACE.
    * REQUEST BODY can be seen in the logs after you execute the test below (same as the PAYLOAD String above).
    * The logged out output of the controller is:
    *
    * Actual: CONTROLLER LOG: {firstKey={thirdKey=MyDto{name='Jackson'}}}
    *
    * Expected: CONTROLLER LOG: {firstKey={secondKey: null, thirdKey=MyDto{name='Jackson'}}}
    *
    * The "secondKey" is completely missing after deserialization which is really important for our use-case.
    * 
    * What am I missing?
    * */
    @Test
    void testJacksonDeserializationWithHashMapValueNullButKeyIsSet() {
        var payload = HttpRequest.POST("/hi", PAYLOAD);
        client.exchange(payload, Map.class).test().awaitDone(5, TimeUnit.SECONDS).assertNoErrors().assertValue(response -> {
            var myDtoResponse = response.body();
            log.info("HTTP response: {}", myDtoResponse);
           return true;
        });
    }

}
