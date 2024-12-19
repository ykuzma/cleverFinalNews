package by.clevertec.news.core.util;

import by.clevertec.news.core.entity.News;
import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.entity.dto.NewsUpdate;
import org.jeasy.random.EasyRandom;

import java.util.List;

public class TestHelper {
    private final EasyRandom easyRandom = new EasyRandom();

    public NewsCreate getNewsCreate() {
        return easyRandom.nextObject(NewsCreate.class);
    }
    public News getNews() {
        return easyRandom.nextObject(News.class);
    }

    public NewsUpdate getNewsUpdate() {
        return easyRandom.nextObject(NewsUpdate.class);
    }
    public <T> List<T> getNewsList(Class<T> clazz) {
        return easyRandom.objects(clazz, 100).toList();
    }
}
