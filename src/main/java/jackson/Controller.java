package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@io.micronaut.http.annotation.Controller("/endpoint")
public class Controller {

    private static final Logger log = LogManager.getLogger();

    @Post("/mnDeserialization")
    public Map<String, Map<String, String>> mnDeserialization(@Body Map<String, Map<String, String>> payload) {
        log.info("CONTROLLER LOG: {}", payload);
        return payload;
    }

    @Post("/manualDeserialization")
    public Map<String, Map<String, String>> manualDeserialization(@Body String payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var parsed = mapper.readValue(payload, new TypeReference<Map<String, Map<String, String>>>() {});
        log.info("CONTROLLER LOG: {}", parsed);
        return parsed;
    }
}
