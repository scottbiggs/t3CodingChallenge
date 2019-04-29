package sleepfuriously.com.t3codingchallenge.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import sleepfuriously.com.t3codingchallenge.R;
import sleepfuriously.com.t3codingchallenge.model.Photo;

/**
 * Simple Dialog to display the full-resolution of a Photo
 * and its attending details.
 */
public class PhotoDialog extends Dialog {

    //------------------------
    //  constants
    //------------------------

    //------------------------
    //  widgets
    //------------------------

    //------------------------
    //  data
    //------------------------

    private Photo mPhoto;

    private Context mCtx;

    //------------------------
    //  methods
    //------------------------

    public PhotoDialog(Photo photo, @NonNull Context context) {
        super(context);
        mPhoto = photo;
        mCtx = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.photo_dialog);

        ImageView photo_iv = findViewById(R.id.photo_iv);
        TextView title_tv = findViewById(R.id.photo_title);
        TextView albumId_tv = findViewById(R.id.photo_album_id);
        TextView id_tv = findViewById(R.id.photo_id);

        // This bypasses the Presenter. todo: get images without breaking MVP pattern
        Picasso.with(mCtx).load(mPhoto.photoUrl).into(photo_iv);
        title_tv.setText(mPhoto.title);
        albumId_tv.setText(Long.toString(mPhoto.albumId));
        id_tv.setText(Long.toString(mPhoto.id));

        Button ok_btn = findViewById(R.id.ok_btn);
        ok_btn.setText(R.string.ok);    // Unknown why this needs to be set here instead of xml file
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
