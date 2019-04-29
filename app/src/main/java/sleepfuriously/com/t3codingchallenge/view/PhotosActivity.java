package sleepfuriously.com.t3codingchallenge.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import sleepfuriously.com.t3codingchallenge.R;
import sleepfuriously.com.t3codingchallenge.model.Album;

import android.view.MenuItem;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 */
public class PhotosActivity extends AppCompatActivity {

    //------------------------
    //  constants
    //------------------------

    private static final String DTAG = PhotosActivity.class.getSimpleName();

    //------------------------
    //  data
    //------------------------

    /** The album that these photos belong to */
    private Album mAlbum;

    /** ID to access the album that holds all these photos */
    private long mAlbumId;


    //------------------------
    //  widgets
    //------------------------

    //------------------------
    //  methods
    //------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_activity);

        mAlbumId = getIntent().getLongExtra(PhotosFragment.ALBUM_ID_KEY, -1);
        if (mAlbumId == -1) {
            Log.e(DTAG, "unable to get albumId!");
        }

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // If the savedInstanceState is not null, no need to figure this
        // out as it's done automatically.
        if (savedInstanceState == null) {

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle bundle = new Bundle();
            bundle.putLong(PhotosFragment.ALBUM_ID_KEY, mAlbumId);

            PhotosFragment frag = new PhotosFragment();
            frag.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, frag)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
