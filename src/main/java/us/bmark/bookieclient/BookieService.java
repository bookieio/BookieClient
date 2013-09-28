package us.bmark.bookieclient;

import retrofit.http.*;

public interface BookieService {

    @GET("/api/v1/bmarks")
    BookmarkList listBookmarks(
            @Query("count") int count,
            @Query("page") int page
    );

    @GET("/api/v1/{user}/bmarks")
    BookmarkList listUserBookmarks(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @Query("count") int count,
            @Query("page") int page
    );

}
