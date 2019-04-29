package sleepfuriously.com.t3codingchallenge.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sleepfuriously.com.t3codingchallenge.model.Album;
import sleepfuriously.com.t3codingchallenge.model.Photo;

/**
 * All access to data comes through this class (using the
 * Singleton Pattern).
 *
 * The network and caching is done here.
 */
public class ModelWindow {

    //------------------------
    //  constants
    //------------------------

    private static final String DTAG = ModelWindow.class.getSimpleName();

    /** Probably not used, but provided for completeness. */
    private static final String ALBUM_SUCCESS_MSG = "Album retrieval successful.";

    /** URL base for all data access for this project */
    public static final String URL_BASE = "https://jsonplaceholder.typicode.com/";

    /** URL for accessing the album list */
    public static final String ALBUM_LIST_URL = URL_BASE + "albums";

    /** URL for accessing the photos list */
    public static final String PHOTOS_LIST_URL = URL_BASE + "photos";

    /**
     * URL prefix for accessing the list of photos associated with an
     * Album.  Simply append the Album's id (converted to a String of course)
     * to the end of this String and you're done.
     */
    private static final String PHOTO_LIST_URL_PREFIX = PHOTOS_LIST_URL + "?albumId=";

    //------------------------
    //  data
    //------------------------

    private static ModelWindow mInstance = null;


    //------------------------
    //  methods
    //------------------------

    private ModelWindow() {
    }

    /**
     * As per the Singleton Pattern, return an instance of this
     * class.
     */
    public static ModelWindow getInstance() {
        if (mInstance == null) {
            mInstance = new ModelWindow();
        }
        return  mInstance;
    }

    /**
     * Returns a list of all the albums to display.
     *
     * @param listener  The instance that implements {@link ModelWindowAlbumsListener}.
     *                  Its {@link ModelWindowAlbumsListener#returnAlbumList(List, boolean, String)}
     *                  method will be called when the value has been retrieved.
     *
     * @param ctx   Ye good ol' Context.
     */
    public void getAlbumList(final ModelWindowAlbumsListener listener, Context ctx) {

        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ALBUM_LIST_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Album> albumList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject)response.get(i);
                                albumList.add(new Album(jsonObject));
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listener.returnAlbumList(albumList, true, ALBUM_SUCCESS_MSG);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.returnAlbumList(null, false, error.getMessage());
                    }
                });
        q.add(request);
    }


    /**
     *
     * @param listener  A class instance that implements {@link ModelWindowAlbumsListener}.
     *                  It's {@link ModelWindowPhotosListener#returnPhotoList(List, boolean, String)}
     *                  will be called once the data is read.
     *
     * @param url       The url to grab the photos from.
     *
     * @param ctx       Everything needs a Context!
     */
    public void getPhotoList(final ModelWindowPhotosListener listener, String url, Context ctx) {

        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Photo> photoList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject)response.get(i);
                                photoList.add(new Photo(jsonObject));
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listener.returnPhotoList(photoList, true, ALBUM_SUCCESS_MSG);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.returnPhotoList(null, false, error.getMessage());
                    }
                });
        q.add(request);
    }

    /**
     * Given an album, this returns a Photos list associated with that album.
     * The resulting data will be returned via a callback in {@link ModelWindowPhotosListener#returnPhotoList(List, boolean, String)}<br>
     * <br>
     * This is a helper function that may be a little more useful than
     * {@link #getPhotoList(ModelWindowPhotosListener, String, Context)}.
     */
    public void getPhotoListFromAlbum(final ModelWindowPhotosListener listener, Album album, Context ctx) {
        String url = PHOTO_LIST_URL_PREFIX + album.id;
        getPhotoList(listener, url, ctx);
    }

    /**
     * Another helper function, this gets the photo list from just the id of the album.
     * See {@link #getPhotoListFromAlbum(ModelWindowPhotosListener, Album, Context)} and
     * {@link #getPhotoList(ModelWindowPhotosListener, String, Context)} for more.
     */
    public void getPhotoListFromAlbumId(final ModelWindowPhotosListener listener, long id, Context ctx) {
        String url = PHOTO_LIST_URL_PREFIX + id;
        getPhotoList(listener, url, ctx);
    }

    //------------------------
    //  interfaces
    //------------------------

    /**
     * Implement this interface to receive a callback when the
     * Albums list is ready.
     */
    public interface ModelWindowAlbumsListener {

        /**
         * Called when the Album list is ready.
         *
         * @param albums     A List of Albums
         * @param successful Tells if the request was successful
         * @param msg        If not successful, an error message
         */
        void returnAlbumList(List<Album> albums, boolean successful, String msg);
    }


    /**
     * Implement this to get a callback when a Photos list
     * is ready.
     */
    public interface ModelWindowPhotosListener {
        /**
         * Callback for when the photos are ready.
         *
         * @param photos        A list of Photos
         *
         * @param successful    TRUE means photos were successfully loaded
         *
         * @param msg           If not successful, error message
         */
        void returnPhotoList(List<Photo> photos, boolean successful, String msg);
    }

}
