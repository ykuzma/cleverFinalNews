package by.clevertec.news.core.util;

import by.clevertec.news.core.domain.News;
import org.jeasy.random.EasyRandom;

public class TestHelper {
    private final EasyRandom easyRandom = new EasyRandom();
    public News getNews() {
        return easyRandom.nextObject(News.class);
    }
   /*

    public NewsCreate getNewsCreate() {
        return easyRandom.nextObject(NewsCreate.class);
    }


    public NewsUpdate getNewsUpdate() {
        return easyRandom.nextObject(NewsUpdate.class);
    }
    public <T> List<T> getNewsList(Class<T> clazz) {
        return easyRandom.objects(clazz, 100).toList();
    }*/
}
