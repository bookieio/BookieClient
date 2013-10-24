package us.bmark.bookieclient;

import java.util.List;

public class SearchResult {
    public String username;
    public List<Bookmark> search_results;
    public Boolean with_content;
    public String phrase;
    public int page;
    public int result_count;
}
