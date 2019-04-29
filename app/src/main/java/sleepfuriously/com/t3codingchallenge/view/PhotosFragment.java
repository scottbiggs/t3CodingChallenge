package sleepfuriously.com.t3codingchallenge.view;

import android.app.Activity;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sleepfuriously.com.t3codingchallenge.R;
import sleepfuriously.com.t3codingchallenge.dummy.DummyContent;
import sleepfuriously.com.t3codingchallenge.model.Album;
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

    /** Useful reference for finding views */
    private View mParentView;

    /** We don't really need the album, but just the ID */
    private long mAlbumId;

    /** Holds all the photos to display (the main purpose of this Fragment) */
    private List<Photo> mPhotoList;

    private PhotosRVAdapter mPhotosAdapter;


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

        // todo: load album info
        mAlbumId =  getArguments().getLong(ALBUM_ID_KEY);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(null);    // todo: change this to something meaningful
        }

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.photos_list, container, false);
        mParentView = container;

        mPhotosRecyclerView = rootView.findViewById(R.id.photo_list_rv);

        // Start the callback to get all the photos from a given
        ModelWindow mw = ModelWindow.getInstance(getContext());
        mw.getPhotoListFromAlbumId(this, mAlbumId, getContext());

        return rootView;
    }



    @Override
    public void returnPhotoList(List<Photo> photos, boolean successful, String msg) {

        mPhotoList = photos;

        // Setup the RecyclerView to display the newly-acquired list of photos

//        mPhotosRecyclerView.setLayoutManager();   todo: set to grid manager

        mPhotosAdapter = new PhotosRVAdapter(this, mPhotoList);

        mPhotosRecyclerView.setAdapter(mPhotosAdapter);
        mPhotosRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

    }

}
