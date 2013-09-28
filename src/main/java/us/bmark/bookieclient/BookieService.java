package us.bmark.bookieclient;

import retrofit.http.*;

public interface BookieService {

    @GET("/api/v1/bmarks")
    BookmarkList everyonesRecent(
            @Query("count") int count,
            @Query("page") int page
    );

    @GET("/api/v1/{user}/bmarks")
    BookmarkList recent(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @Query("count") int count,
            @Query("page") int page
    );

    @GET("/api/v1/{user}/bmarks/{tag}")
    BookmarkList tagged(
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

    @DELETE("/api/v1/{user}/bmark/{bmid}")
    DeleteBookmarkResponse delete(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @Path("bmid") String bmid
    );

}
