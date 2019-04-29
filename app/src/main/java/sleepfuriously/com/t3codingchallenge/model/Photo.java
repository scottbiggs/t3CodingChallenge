package sleepfuriously.com.t3codingchallenge.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Describes a Photo.  Very simple.
 */
public class Photo {

    //------------------------
    //  constants
    //------------------------

    /** Keys to get the class data from JSON objects */
    public static final String
            ALBUM_ID_KEY = "albumId",
            ID_KEY = "id",
            TITLE_KEY = "title",
            URL_KEY = "url",
            THUMBNAIL_URL_KEY = "thumbnailUrl";

    //------------------------
    //  data
    //------------------------

    public long albumId;
    public long id;
    public String title;
    public String photoUrl;
    public String thumbnailUrl;

    //------------------------
    //  methods
    //------------------------

    public Photo (long albumId, long id, String title, String url, String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.photoUrl = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Photo (JSONObject jsonObject) {
        try {
            albumId = jsonObject.getLong(ALBUM_ID_KEY);
            id = jsonObject.getLong(ID_KEY);
            title = jsonObject.getString(TITLE_KEY);
            photoUrl = jsonObject.getString(URL_KEY);
            thumbnailUrl = jsonObject.getString(THUMBNAIL_URL_KEY);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
