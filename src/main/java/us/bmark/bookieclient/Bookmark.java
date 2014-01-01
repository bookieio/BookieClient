package us.bmark.bookieclient;


import java.util.List;

public class Bookmark {
    public String description;
    public String url;
    public String hash_id;
    public String username;
    public String stored;
    public int clicks;
    public int total_clicks;
    public List<Tag> tags;
    public String tag_str;
}
