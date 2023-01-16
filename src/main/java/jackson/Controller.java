package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@io.micronaut.http.annotation.Controller("/endpoint")
public class Controller {

    private static final Logger log = LogManager.getLogger();

    @Inject
    ObjectMapper objectMapper;

    @Post("/mnDeserialization")
    public Map<String, Map<String, MyDto>> mnDeserialization(@Body Map<String, Map<String, MyDto>> payload) {
        log.info("CONTROLLER LOG: {}", payload);
        // DOES NOT WORK
        // LOGGER OUTPUT
        // ACTUAL: {rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}}}
        // EXPECTED: {rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}
        // NOTE: the 'key2' is missing.

        // Ignore the return value.
        return payload;
    }

    @Post("/manualDeserialization")
    public Map<String, Map<String, MyDto>> manualDeserialization(@Body String payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var parsed = mapper.readValue(payload, new TypeReference<Map<String, Map<String, MyDto>>>() {});
        log.info("CONTROLLER LOG: {}", parsed);
        // WORKS
        // LOGGER OUTPUT
        // ACTUAL: {rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}
        // EXPECTED: {rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}
        // NOTE: the 'key2' is present and the value is 'null' as expected/wanted

        // Ignore the return value.
        return parsed;
    }

    @Post("/usingWrapperClass")
    public WrapperClass usingWrapperClass(@Body WrapperClass wrapperClass) {
        log.info("CONTROLLER LOG: {}", wrapperClass);
        // WORKS
        // CONTROLLER LOGGER OUTPUT
        // ACTUAL: WrapperClass{wrapperClass={rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}}
        // EXPECTED: WrapperClass{wrapperClass={rootKey={key1=MyDto{mapOfStrings={Jackson=GOOD, John=BETTER, Alice=BEST}, anotherValue='Some random string.'}, key2=null}}}
        // NOTE: the 'key2' is present and the value is 'null' as expected/wanted

        // Ignore the return value.
        return wrapperClass;
    }
}
