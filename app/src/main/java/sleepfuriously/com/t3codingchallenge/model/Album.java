package sleepfuriously.com.t3codingchallenge.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Describes an Album datum.  Very simple.
 */
public class Album {

    //------------------------
    //  constants
    //------------------------

    private static final String DTAG = Album.class.getSimpleName();

    /** Keys to get the class data from JSON objects */
    public static final String
            USERID_KEY = "userId",
            ID_KEY = "id",
            TITLE_KEY = "title";

    //------------------------
    //  data
    //------------------------

    public long userId;
    public long id;
    public String title;

    //------------------------
    //  methods
    //------------------------

    public Album(long userId, long id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public Album (JSONObject jsonObject) {
        try {
            userId = jsonObject.getLong(USERID_KEY);
            id = jsonObject.getLong(ID_KEY);
            title = jsonObject.getString(TITLE_KEY);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
