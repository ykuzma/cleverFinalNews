package by.clevertec.news.api;

import by.clevertec.news.api.util.RestDocHelper;
import by.clevertec.news.api.util.TestHelper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

public abstract class AbstractTest {
    protected RestDocHelper docHelper;
    protected TestHelper testHelper;
    protected ObjectMapper objectMapper;

    public  AbstractTest() {
        docHelper = new RestDocHelper();
        testHelper = new TestHelper();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules()
                .setDateFormat(new StdDateFormat())
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }
}
