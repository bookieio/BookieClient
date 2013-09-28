package us.bmark.bookieclient;

import retrofit.http.*;

import java.util.EmptyStackException;

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

    @GET("/api/v1/{user}/bmarks/{tag}")
    BookmarkList listUserBookmarkTagged(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @Path("tag") String tag,
            @Query("count") int count,
            @Query("page") int page
    );

    @POST("/api/v1/{user}/bmark")
    NewBookmarkResponse bookmark(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @Body NewBookmark bmark
    );


}
