package by.clevertec.news.core.util;

import by.clevertec.news.core.domain.News;
import org.jeasy.random.EasyRandom;

import java.util.List;

public class TestHelper {
    private final EasyRandom easyRandom = new EasyRandom();
    public News getNews() {
        return easyRandom.nextObject(News.class);
    }

    public <T> List<T> getObjectList(Class<T> clazz) {
        return easyRandom.objects(clazz, 100).toList();}

}
