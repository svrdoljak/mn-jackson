package jackson;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@MicronautTest
class JacksonTest {

    private static final String PAYLOAD = """
                {
                    "rootKey": {
                        "key1": {
    	                    "mapOfStrings": {
		                        "Jackson": "GOOD",
		                        "John": "BETTER",
		                        "Alice": "BEST"
		                    },
		                    "anotherValue": "Some random string."
		                },
		                "key2": null
                    }  
                }
                """;

    private static final String WRAPPER_CLASS_PAYLOAD = """
                {
                    "wrapperClass": {
                        "rootKey": {
                            "key1": {
                                "mapOfStrings": {
                                    "Jackson": "GOOD",
                                    "John": "BETTER",
                                    "Alice": "BEST"
                                },
                                "anotherValue": "Some random string."
                            },
                            "key2": null
                        }
                    }
                }
                """;

    @Inject
    @Client("/endpoint")
    Rx3HttpClient client;

    @Test
    void mnDeserializtion() {
        var payload = HttpRequest.POST("/mnDeserialization", PAYLOAD);
        client.exchange(payload).test().awaitDone(5, TimeUnit.SECONDS).assertNoErrors().assertValue(response -> {
            // DOES NOT WORK
            // CONTROLLER LOGGER OUTPUT
            // ACTUAL: {rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}}}
            // EXPECTED: {rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}
            // NOTE: the 'key2' is missing.
            return true;
        });
    }

    @Test
    void manualDeserialization() {
        var payload = HttpRequest.POST("/manualDeserialization", PAYLOAD);
        client.exchange(payload).test().awaitDone(5, TimeUnit.SECONDS).assertNoErrors().assertValue(response -> {
            // WORKS
            // CONTROLLER LOGGER OUTPUT
            // ACTUAL: {rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}
            // EXPECTED: {rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}
            // NOTE: the 'key2' is present and the value is 'null' as expected/wanted
            return true;
        });
    }

    @Test
    void usingWrapperClass() {
        var payload = HttpRequest.POST("/usingWrapperClass", WRAPPER_CLASS_PAYLOAD);
        client.exchange(payload).test().awaitDone(5, TimeUnit.SECONDS).assertNoErrors().assertValue(response -> {
            // WORKS
            // CONTROLLER LOGGER OUTPUT
            // ACTUAL: WrapperClass{wrapperClass={rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}}
            // EXPECTED: WrapperClass{wrapperClass={rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}}
            // NOTE: the 'key2' is present and the value is 'null' as expected/wanted
            return true;
        });
    }
}
