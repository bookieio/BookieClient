package us.bmark.bookieclient;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class IT {

    RestAdapter adapter;
    BookieService service;

    @Before
    public void setupService() {
        adapter = new RestAdapter.Builder()
                .setServer("https://bmark.us")
                .build();
        service = adapter.create(BookieService.class);
    }

    @Test
    public void getBookmarks() {
        try {
            BookmarkList results = service.listBookmarks();
            for (Bookmark b : results.bmarks) {
                System.out.println(b.url);

                System.out.println(StringUtils.join(b.tags.iterator(), '|'));
            }
            assertThat(results.bmarks, is(not(empty())));
            assertThat(results.bmarks.size(), is(equalTo(results.count)));
        } catch (RetrofitError e) {
            e.printStackTrace();
            System.out.println(e.getUrl());
            System.out.println(e.getResponse().getBody().toString());
            System.out.println(e.isNetworkError());


            fail();
        }
    }

}
