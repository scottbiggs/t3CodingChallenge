package sleepfuriously.com.t3codingchallenge.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sleepfuriously.com.t3codingchallenge.R;
import sleepfuriously.com.t3codingchallenge.model.Photo;

/**
 * Adapter for the ReyclerView that shows all the photo thumbnails
 * for a given album.
 */
public class PhotosRVAdapter
        extends RecyclerView.Adapter<PhotosRVAdapter.ViewHolder> {

    //------------------------
    //  constants
    //------------------------

    private static final String DTAG = PhotosRVAdapter.class.getSimpleName();

    //------------------------
    //  data
    //------------------------

    private final List<Photo> mPhotoList;

    private final PhotosFragment mParentFragment;

    //------------------------
    //  listener
    //------------------------

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int pos = (int) v.getTag();
            PhotoDialog dialog = new PhotoDialog(mPhotoList.get(pos), v.getContext());
            dialog.show();

        }
    };

    //------------------------
    //  methods
    //------------------------

    PhotosRVAdapter(PhotosFragment parent, List<Photo> photos) {
        mParentFragment = parent;
        mPhotoList = photos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_thumb_item, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Photo photo = mPhotoList.get(position);

        // This bypasses the Presenter. todo: get images without breaking MVP pattern
        Picasso.with(mParentFragment.getContext()).load(photo.thumbnailUrl).into(holder.thumbnail);
        holder.title.setText(photo.title);
        holder.albumId.setText(Long.toString(photo.albumId));
        holder.id.setText(Long.toString(photo.id));

        // store some info to be retrieved later when clicked
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //  classes
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView thumbnail;
        final TextView title;
        final TextView albumId;
        final TextView id;

        ViewHolder(View v) {
            super(v);
            thumbnail = v.findViewById(R.id.photo_thumb_iv);
            title = v.findViewById(R.id.title_tv);
            albumId = v.findViewById(R.id.album_id_tv);
            id = v.findViewById(R.id.id_tv);
        }
    }

}
