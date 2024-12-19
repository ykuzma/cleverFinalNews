package by.clevertec.news.core.util;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class Pagination {
    private static final int DEFAULT_PAGE_SIZE = 5;
    public static final int DEFAULT_PAGE_NUMBER = 0;
    @Positive(message = "Page size must not be less than one")
    private int pageSize;
    @PositiveOrZero(message = "Page index must not be less than zero")
    private int pageNumber;

    public Pagination() {
        pageSize = DEFAULT_PAGE_SIZE;
        pageNumber = DEFAULT_PAGE_NUMBER;
    }


}
