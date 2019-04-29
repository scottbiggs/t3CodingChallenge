package sleepfuriously.com.t3codingchallenge.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import sleepfuriously.com.t3codingchallenge.R;
import sleepfuriously.com.t3codingchallenge.model.Album;
import sleepfuriously.com.t3codingchallenge.presenter.ModelWindow;

/**
 * Starting point.  This works fine with both tablets and phones.
 */
public class MainActivity extends AppCompatActivity
    implements ModelWindow.ModelWindowAlbumsListener {

    //------------------------
    //  constants
    //------------------------

    private static final String DTAG = MainActivity.class.getSimpleName();

    //------------------------
    //  widgets
    //------------------------

    /** Displays album list */
    private RecyclerView mAlbumsRecyclerView;

    //------------------------
    //  data
    //------------------------

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    /** Adapter for {@link MainActivity#mAlbumsRecyclerView}. */
    private AlbumRVAdapter mAlbumAdapter;

    //------------------------
    //  methods
    //------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // First things first, gotta be on the  internet!
        if (!isInternetAvailable()) {
            View v = findViewById(android.R.id.content);
            Snackbar.make(v, R.string.no_internet_warning, Snackbar.LENGTH_LONG).show();
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // identify the albums recyclerview and request it to get filled
        mAlbumsRecyclerView = findViewById(R.id.albums_rv);
        assert mAlbumsRecyclerView != null;

        ModelWindow mw = ModelWindow.getInstance();
        // todo: start a waiting dialog to let user know something is happening
        mw.getAlbumList(this, this);
    }


    /**
     * Called when the album list is ready.
     *
     * @param albums        A List of Albums
     *
     * @param successful    Tells if the request was successful
     *
     * @param msg           If not successful, an error message
     */
    @Override
    public void returnAlbumList(List<Album> albums, boolean successful, String msg) {

        if (!successful) {
            Log.e(DTAG, "returnAlbumList() is unsuccessful. Msg = " + msg);
            Toast.makeText(this, R.string.no_internet_warning, Toast.LENGTH_LONG).show();
            finish();
            return; // might be redundant?
        }

        mAlbumAdapter = new AlbumRVAdapter(this, albums, mTwoPane);
        mAlbumsRecyclerView.setAdapter(mAlbumAdapter);

        // todo: disable waiting dialog
    }


    /**
     * Goes through the ConnectivityManager to determine if this
     * app has access to the internet currently.
     */
    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null)
                && activeNetworkInfo.isAvailable()
                && activeNetworkInfo.isConnected();
    }

}
