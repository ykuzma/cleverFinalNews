package by.clevertec.news.core.service.impl;

import by.clevertec.news.core.domain.News;
import by.clevertec.news.core.port.out.CommentOutputPort;
import by.clevertec.news.core.port.out.NewsOutputPort;
import by.clevertec.news.core.service.NewsUseCase;
import by.clevertec.news.core.util.Pagination;
import by.clevertec.news.core.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsUseCaseImpl implements NewsUseCase {

    private final NewsOutputPort adapter;
    private final CommentOutputPort commentAdapter;
    private final UtilService utilService;


    @Override
    public News findById(UUID id) {
        return adapter.findById(id);
    }

    @Override
    public News findWithCommentsById(UUID id) {
        News news = findById(id);
        news.setComments(commentAdapter.findByNews(id));
        return news;
    }

    @Override
    public News addNews(News news) {
        news.setTime(utilService.getCurrentTime());
        return adapter.save(news);
    }

    @Override
    public void delete(UUID id) {
        adapter.delete(adapter.findById(id));
        commentAdapter.deleteCommentByNews(id);
    }

    @Override
    public News update(News newsUpdate, UUID id) {
        News existingNews = adapter.findById(id);
        return existingNews.update(newsUpdate);
    }

    @Override
    public List<News> findAll(Pagination pagination) {
        return adapter.findAll(PageRequest.of(pagination.getPageNumber(), pagination.getPageSize()));
    }
}
