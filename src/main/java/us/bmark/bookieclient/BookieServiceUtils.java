package us.bmark.bookieclient;


public class BookieServiceUtils {
    public static String urlForRedirect(Bookmark bmark, String baseUrl, String username) {
        StringBuilder uriString = new StringBuilder(baseUrl);
        if (equalButNotBlank(bmark.username, username)) {
            uriString.append("/").append(username);
        }
        uriString.append("/redirect/").append(bmark.hash_id);
        return uriString.toString();
    }

    private static boolean equalButNotBlank(String lhs, String rhs) {
        return (lhs != null) && !lhs.isEmpty() && lhs.equals(rhs);
    }
}
