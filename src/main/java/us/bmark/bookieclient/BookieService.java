package us.bmark.bookieclient;

import retrofit.http.GET;

import java.util.List;

public interface BookieService {

    @GET("/api/v1/bmarks")
    BookmarkList listBookmarks();
}
