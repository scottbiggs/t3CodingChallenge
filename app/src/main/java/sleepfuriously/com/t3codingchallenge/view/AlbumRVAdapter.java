package sleepfuriously.com.t3codingchallenge.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import sleepfuriously.com.t3codingchallenge.R;
import sleepfuriously.com.t3codingchallenge.model.Album;

/**
 * todo: Adapter for album list
 */
public class AlbumRVAdapter
            extends RecyclerView.Adapter<AlbumRVAdapter.ViewHolder> {

    //------------------------
    //  constants
    //------------------------

    private static final String DTAG = AlbumRVAdapter.class.getSimpleName();

    //------------------------
    //  data
    //------------------------

    private final MainActivity mParentActivity;

    /** Holds the Adapter's copy of the data to display (list of Albums) */
    private final List<Album> mAlbumList;

    /** TRUE iff in two-pane mode (tablet) */
    private final boolean mTwoPane;

    /** The currently selected item in the RV--needed for highlighting. */
    private int mSelected = -1;

    /** Normal background color for an Album list item */
    private int mNormalBackground;

    /** The color to use for an Album list item that is currently selected */
    private int mHighlightBackground;


    //------------------------
    //  listener
    //------------------------

    /** Click Listener for the items in the RecyclerView. */
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // The tag tells which album we're dealing with
            int currentlySelected = (int) view.getTag();
            Album album = mAlbumList.get(currentlySelected);

            // redraw the selection
            notifyItemChanged(mSelected);
            notifyItemChanged(currentlySelected);
            mSelected = currentlySelected;

            if (mTwoPane) {
                // Start the new Fragment
                Bundle bundle = new Bundle();
                bundle.putLong(PhotosFragment.ALBUM_ID_KEY, album.id);

                PhotosFragment frag = new PhotosFragment();
                frag.setArguments(bundle);

                FragmentManager mgr = mParentActivity.getSupportFragmentManager();
                FragmentTransaction transaction = mgr.beginTransaction();

                // Replace the Fragment in the item_detail_container with the new Fragment.
                transaction.replace(R.id.item_detail_container, frag);
                transaction.commit();
            }

            else {
                // Start new Activity
                Context context = view.getContext();
                Intent intent = new Intent(context, PhotosActivity.class);

                intent.putExtra(PhotosFragment.ALBUM_ID_KEY, album.id);
                context.startActivity(intent);
            }
        }
    };

    //------------------------
    //  methods
    //------------------------

    AlbumRVAdapter(MainActivity parent,
                   List<Album> items,
                   boolean twoPane) {
        mAlbumList = items;
        mParentActivity = parent;
        mTwoPane = twoPane;

        // Calculate the colors to avoid slowing down onBindViewHolder
        mNormalBackground = parent.getResources().getColor(R.color.album_item_background_normal);
        mHighlightBackground = parent.getResources().getColor(R.color.album_item_background_highlight);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_list_item, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        // set data
        Album album = mAlbumList.get(position);
        holder.albumNameTv.setText(album.title);

        // Store the adapter position of this item for use during onClick
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mOnClickListener);

        // Possibly highlight this as a selected item
        if (position == mSelected) {
            holder.backgroundView.setBackgroundColor(mHighlightBackground);
        }
        else {
            holder.backgroundView.setBackgroundColor(mNormalBackground);
        }
    }


    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //  classes
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView albumNameTv;
        final View backgroundView;  // allows access to the background color

        ViewHolder(View view) {
            super(view);
            albumNameTv = view.findViewById(R.id.album_name_tv);
            backgroundView = view.findViewById(R.id.album_item_background);
        }
    }

}
