package sleepfuriously.com.t3codingchallenge.view;

import android.app.Activity;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sleepfuriously.com.t3codingchallenge.R;
import sleepfuriously.com.t3codingchallenge.dummy.DummyContent;
import sleepfuriously.com.t3codingchallenge.model.Album;

/**
 * Displays the photographs of a given Album.<br>
 * <br>
 * On tablets, this is the 2nd pane of the {@link MainActivity}.
 * But on phones, this is displayed within {@link PhotosActivity}.
 */
public class PhotosFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    @Deprecated
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Use this key to pass the Album ID to this Fragment.
     */
    public static final String ALBUM_ID_KEY = "album_id_key";

    /**
     * The dummy content this fragment is presenting.
     */
//    private DummyContent.DummyItem mItem;

    /** The album to display (and its associated images */
    private Album mAlbum;

    // todo: make list of photos


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PhotosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            // todo: load album info
            mAlbum = new Album(0, 0, "test album");
            mAlbum.id = getArguments().getLong(ALBUM_ID_KEY);

            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mAlbum.title);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.photo_thumb_item, container, false);

        // Show the dummy content as text in a TextView.
        if (mAlbum != null) {
            ((TextView) rootView.findViewById(R.id.album_id_tv)).setText(mAlbum.title);
        }

        return rootView;
    }
}
