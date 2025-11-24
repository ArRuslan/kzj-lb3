package ua.nure.kz.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pagination {
    private boolean hasPrev;
    private boolean hasNext;
    private Page[] pages;
    private String addQuery;
    private long prevPage;
    private long nextPage;
    private int pageSize;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class Page {
        private boolean isCurrent;
        private long num;
    }

    public static Pagination make(long currentPage, int pageSize, long totalEntries, String addQuery) {
        long totalPages = (totalEntries + pageSize - 1) / pageSize;

        long minPage = Math.max(currentPage - 2, 1);
        long maxPage = Math.min(currentPage + 2, totalPages);

        Page[] pages = new Page[(int)(maxPage - minPage + 1)];
        int i = 0;
        for(long page = minPage; page <=maxPage; ++page, ++i) {
            pages[i] = new Page(page == currentPage, page);
        }

        return new Pagination(
                currentPage > 1,
                currentPage < totalPages,
                pages,
                addQuery,
                currentPage > 1 ? currentPage - 1 : currentPage,
                currentPage < totalPages ? currentPage + 1 : currentPage,
                pageSize);
    }
}
