package sleepfuriously.com.t3codingchallenge.model;

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

    /** URL for accessing the album list */
    private static final String ALBUM_LIST_URL = "https://jsonplaceholder.typicode.com/albums";


    //------------------------
    //  data
    //------------------------

    private static ModelWindow mInstance = null;


    //------------------------
    //  methods
    //------------------------

    private ModelWindow (Context ctx) {
        // todo
    }

    /**
     * As per the Singleton Pattern, return an instance of this
     * class.
     */
    public static ModelWindow getInstance (Context ctx) {
        if (mInstance == null) {
            mInstance = new ModelWindow(ctx);
        }
        return  mInstance;
    }

    /**
     * Returns a list of all the albums to display.
     *
     * @param listener  The instance that implements {@link ModelWindowListener}.
     *                  Its {@link ModelWindowListener#returnAlbumList(List, boolean, String)}
     *                  method will be called when the value has been retrieved.
     *
     * @param ctx   Ye good ol' Context.
     */
    public void getAlbumList(final ModelWindowListener listener, Context ctx) {

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


//        // this gets the pure data
//        RequestQueue q = Volley.newRequestQueue(ctx);
//        StringRequest request = new StringRequest(Request.Method.GET, ALBUM_LIST_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        List<Album> albumList = parseJsonToAlbums(response);
//                        listener.returnAlbumList(albumList, true, ALBUM_SUCCESS_MSG);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        listener.returnAlbumList(null, false, error.getMessage());
//                    }
//                });
    }

    //------------------------
    //  interfaces
    //------------------------

    public interface ModelWindowListener {

        /**
         * Response to a request to get the Album list.
         *
         * @param albums        A List of Albums
         *
         * @param successful    Tells if the request was successful
         *
         * @param msg           If not successful, an error message
         */
        void returnAlbumList(List<Album> albums, boolean successful, String msg);
    }
}
