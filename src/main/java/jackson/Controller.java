package jackson;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@io.micronaut.http.annotation.Controller("/endpoint")
public class Controller {

    private static final Logger log = LogManager.getLogger();

    @Post("/hi")
    public Map<String, Map<String, MyDto>> getMyDto(@Body Map<String, Map<String, MyDto>> myDto) {
        log.info("CONTROLLER LOG: {}", myDto);
        return myDto;
    }
}
