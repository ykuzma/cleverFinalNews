package by.clevertec.news.core.util;

import by.clevertec.news.core.entity.dto.NewsCreate;
import org.jeasy.random.EasyRandom;

public class TestHelper {
    private final EasyRandom easyRandom = new EasyRandom();

    public NewsCreate getNewsCreate() {
        return easyRandom.nextObject(NewsCreate.class);
    }
}
