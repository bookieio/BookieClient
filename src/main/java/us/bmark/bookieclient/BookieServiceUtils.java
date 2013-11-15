package us.bmark.bookieclient;


import org.jetbrains.annotations.NonNls;

public class BookieServiceUtils {
    public static String urlForRedirect(Bookmark bmark, String baseUrl, String username) {
        StringBuilder uriString = new StringBuilder(baseUrl);
        if (equalButNotBlank(bmark.username, username)) {
            uriString.append("/").append(username);
        }
        uriString.append("/redirect/"); //NON-NLS
        uriString.append(bmark.hash_id);
        return uriString.toString();
    }

    private static boolean equalButNotBlank(@NonNls String lhs, String rhs) {
        return (lhs != null) && !lhs.isEmpty() && lhs.equals(rhs);
    }
}
