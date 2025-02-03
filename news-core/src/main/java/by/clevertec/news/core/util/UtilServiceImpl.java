package by.clevertec.news.core.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UtilServiceImpl implements UtilService{

    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
