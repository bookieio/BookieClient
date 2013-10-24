package us.bmark.bookieclient;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface BookieService {

    @GET("/api/v1/bmarks")
    public BookmarkList everyonesRecent(
            @Query("count") int count,
            @Query("page") int page
    );

    @GET("/api/v1/{user}/bmarks")
    public BookmarkList recent(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @Query("count") int count,
            @Query("page") int page
    );

    @GET("/api/v1/{user}/bmarks/{tag}")
    public BookmarkList tagged(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @Path("tag") String tag,
            @Query("count") int count,
            @Query("page") int page
    );

    @GET("/api/v1/{user}/bmarks/search/{terms}")
    public SearchResult search(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @EncodedPath("terms") String terms,
            @Query("count") int count,
            @Query("page") int page
    );

    @POST("/api/v1/{user}/bmark")
    public NewBookmarkResponse bookmark(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @Body NewBookmark bmark
    );

    @DELETE("/api/v1/{user}/bmark/{bmid}")
    public DeleteBookmarkResponse delete(
            @Path("user") String user,
            @Query("api_key") String apikey,
            @Path("bmid") String bmid
    );

}
