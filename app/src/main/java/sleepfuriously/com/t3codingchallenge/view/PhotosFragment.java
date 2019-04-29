package sleepfuriously.com.t3codingchallenge.view;

import android.app.Activity;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sleepfuriously.com.t3codingchallenge.R;
import sleepfuriously.com.t3codingchallenge.model.Photo;
import sleepfuriously.com.t3codingchallenge.presenter.ModelWindow;

/**
 * Displays the photograph thumbnails of a given Album.<br>
 * <br>
 */
public class PhotosFragment extends Fragment
        implements ModelWindow.ModelWindowPhotosListener {

    //------------------------
    //  constants
    //------------------------

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

    //------------------------
    //  widgets
    //------------------------

    private RecyclerView mPhotosRecyclerView;

    //------------------------
    //  data
    //------------------------

    /** We don't really need the album, but just the ID */
    private long mAlbumId;

    /** Holds all the photos to display (the main purpose of this Fragment) */
    private List<Photo> mPhotoList;

    private PhotosRVAdapter mPhotosAdapter;

    /** When TRUE, this is in a 2-pane environment */
    private boolean mTwoPane;

    //------------------------
    //  methods
    //------------------------

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PhotosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAlbumId =  getArguments().getLong(ALBUM_ID_KEY);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            // AppBarLayout only appears here for phones (single pane)
            mTwoPane = false;
            appBarLayout.setTitle(null);    // todo: change this to something meaningful
        }
        else {
            mTwoPane = true;
        }

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.photos_list, container, false);
        mPhotosRecyclerView = rootView.findViewById(R.id.photo_list_rv);

        // Start the callback to get all the photos from a given
        ModelWindow mw = ModelWindow.getInstance();
        // todo: start waiting graphic
        mw.getPhotoListFromAlbumId(this, mAlbumId, getContext());

        return rootView;
    }



    @Override
    public void returnPhotoList(List<Photo> photos, boolean successful, String msg) {

        mPhotoList = photos;

        // Setup the RecyclerView to display the newly-acquired list of photos
        mPhotosAdapter = new PhotosRVAdapter(this, mPhotoList);
        mPhotosRecyclerView.setAdapter(mPhotosAdapter);
        if (mTwoPane) {
            mPhotosRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else {
            mPhotosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        // todo: stop waiting graphic
    }

}
