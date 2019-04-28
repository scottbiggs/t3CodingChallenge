package sleepfuriously.com.t3codingchallenge.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import java.util.List;

import sleepfuriously.com.t3codingchallenge.R;
import sleepfuriously.com.t3codingchallenge.dummy.DummyContent;
import sleepfuriously.com.t3codingchallenge.model.Album;
import sleepfuriously.com.t3codingchallenge.model.ModelWindow;

/**
 * todo: replace with my own comments
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MainActivity extends AppCompatActivity
    implements ModelWindow.ModelWindowListener {

    //------------------------
    //  constants
    //------------------------

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

    //------------------------
    //  methods
    //------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "MAIN - Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        ModelWindow mw = ModelWindow.getInstance(this);
        mw.getAlbumList(this, this);
    }


    @Override
    public void returnAlbumList(List<Album> albums, boolean successful, String msg) {
        Snackbar.make(mAlbumsRecyclerView, "called!", Snackbar.LENGTH_SHORT).show();
        mAlbumsRecyclerView.setAdapter(new AlbumRVAdapter(this, DummyContent.ITEMS, mTwoPane));
    }
}