package us.bmark.bookieclient;

import retrofit.http.GET;
import retrofit.http.Query;

public interface BookieService {

    @GET("/api/v1/bmarks")
    BookmarkList listBookmarks(
            @Query("count") int count,
            @Query("page") int page
    );
}
