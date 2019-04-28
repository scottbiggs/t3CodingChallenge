package sleepfuriously.com.t3codingchallenge.model;

/**
 * Describes a Photo.  Very simple.
 */
public class Photo {

    public long albumId;
    public long id;
    public String title;
    public String url;
    public String thumbnailUrl;

    public Photo (long albumId, long id, String title, String url, String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }
}
