package us.bmark.bookieclient;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NonNls;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@SuppressWarnings({"WeakerAccess", "StringConcatenation", "HardCodedStringLiteral"})
public class IT {

    public static final String SERVER_URL = "https://bmark.us";
    static final RestAdapter adapter =new RestAdapter.Builder()
            .setServer(SERVER_URL).build();
    public static final String TESTING_TAG_1 = "testing-tag-1";

    BookieService service;
    static final String username;
    static final String apikey;
    static final String PROPS_FILE = "/local-it" + SERVER_URL + "properties";
    static final String RESOURCES_PATH = "src/test/resources";

    static {
        Properties props = new Properties();
        InputStream ins = IT.class
                .getResourceAsStream(PROPS_FILE);

        try {
            props.load(ins);
        } catch (Exception e) {
            e.printStackTrace();
            // do nothing here, we'll double check and print
            // in the method checkProperties
        }

        username = props.getProperty("username");
        apikey = props.getProperty("apikey");

    }

    @BeforeClass
    public static void checkProperties() {
        assertThat("username should be set in " + RESOURCES_PATH + PROPS_FILE
                , username, is(notNullValue()));
        assertThat("apikey should be set in " + RESOURCES_PATH + PROPS_FILE
                , apikey, is(notNullValue()));
    }

    @Before
    public void setupService() {
        adapter.setLogLevel(RestAdapter.LogLevel.FULL);
        service = adapter.create(BookieService.class);
    }

    @Test
    public void getBookmarks() {
        try {
            int requestedCount = 13;
            int requestedPage = 2;
            BookmarkList results =
                    service.everyonesRecent(requestedCount, requestedPage);

            assertThat(results.bmarks, is(not(empty())));
            assertThat(results.bmarks.size(), is(equalTo(results.count)));
            assertThat(results.count, is(equalTo(requestedCount)));
        } catch (RetrofitError e) {
            e.printStackTrace();
            System.out.println(e.getUrl());
            System.out.println(e.getResponse().getBody());
            System.out.println(e.isNetworkError());
            fail();
        }
    }

    @Test
    public void getUserBookmarks() {
        try {
            int requestedCount = 5;
            int requestedPage = 1;
            BookmarkList results =
                    service.recent(username, apikey, requestedCount, requestedPage);

            assertThat(results.bmarks, is(not(empty())));
            assertThat(results.bmarks.size(), is(equalTo(results.count)));
            assertThat(results.count, is(equalTo(requestedCount)));
        } catch (RetrofitError e) {
            e.printStackTrace();
            System.out.println(e.getUrl());
            System.out.println(e.getResponse().getBody());
            System.out.println(e.isNetworkError());
            fail();
        }
    }

    @Test
    public void makeBookmarkThenDeleteIt() throws InterruptedException {
        final long TIME = new Date().getTime();
        NewBookmark bmark = new NewBookmark();
        bmark.url= "http://foo" + SERVER_URL + "example.com/testing/java-client-it-test/" +TIME;
        bmark.tags="testing-tag-1 testing-tag-2 testing-java-client-IT-test" + TIME;
        bmark.description="THIS BOOKMARK PLACED BY Java Client Integration Test " + TIME;
        bmark.inserted_by="JAVA-CLIENT-INT-TEST";

        int initialCount = service.tagged(username, apikey, TESTING_TAG_1, 99, 0).count;

        NewBookmarkResponse response = service.bookmark(username,apikey,bmark);
        String hash = response.bmark.hash_id;
        assertThat(hash,is(notNullValue()));

        // allow to settle in
        Thread.sleep(25000L);

        int postCount = service.tagged(username, apikey, TESTING_TAG_1, 99, 0).count;
        assertThat(postCount,is(initialCount+1));


        SearchResult searchResults = service.search(username,apikey,Long.toString(TIME),4,0);

        assertThat(searchResults.search_results,hasItem(withHashId(hash)));

        String deleteRespMsg = service.delete(username,apikey,hash).message;
        assertThat(deleteRespMsg,is(equalTo("done")));
        int finalCount = service.tagged(username, apikey, TESTING_TAG_1, 99, 0).count;
        assertThat(finalCount,is(equalTo(initialCount)));
    }

    @AfterClass
    public static void attemptCleanupOfCreatedBookmarks() {
        BookieService service = adapter.create(BookieService.class);
        List<Bookmark> bmarks = service.tagged(username,apikey, TESTING_TAG_1,199,0).bmarks;
        for(Bookmark bmark : bmarks) {
            service.delete(username,apikey,bmark.hash_id);
        }

    }


    private Matcher<Bookmark> withHashId(final String hash) {
        return new BookmarkWithHashIdMatcher(hash);
    }

    @SuppressWarnings("InstanceofInterfaces")
    private static final class BookmarkWithHashIdMatcher extends BaseMatcher<Bookmark> {

        @NonNls
        private final String hash;

        BookmarkWithHashIdMatcher(String hash) {
            super();
            this.hash = hash;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a bookmark with hash_id " + hash);
        }

        @Override
        public boolean matches(Object item) {
            return (item instanceof Bookmark) && hash.equals(((Bookmark) item).hash_id);
        }

        @Override
        public void describeMismatch(Object item, Description mismatchDescription) {
            if (item instanceof Bookmark) {
                Bookmark bm = (Bookmark) item;
                mismatchDescription.appendText("Item had hash " + bm.hash_id + " but expected " + hash);
            } else {
                mismatchDescription.appendText("The item wasn't even a bookmark");
            }
        }

    }
}
