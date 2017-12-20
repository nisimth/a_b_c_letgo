package course.android.letgo_302838271_305212946.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;

import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.database.MyInfoManager;
import course.android.letgo_302838271_305212946.interfaces.CallBackListiner;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPostInfoFragment extends DialogFragment {
    private static final int CONTENTT_REQUEST = 111;


    private EditText titleField;
    private EditText contentField;
    private ImageView postPhoto;
    private Button saveBtn;
    private Button takePhoto;
    private Spinner spinner;
    private ArrayAdapter <CharSequence> adapter;
    private CallBackListiner callback;
    private int targetRequestCode;
    private Intent intent;
    private Context context;


    public EditPostInfoFragment() {
        // Required empty public constructor
    }

    private View.OnClickListener savePostListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String postTitle = titleField.getText().toString();
            String postContent = contentField.getText().toString();
            String postTag = spinner.getSelectedItem().toString();;
            Bitmap photo = postPhoto.getDrawingCache();

            if(targetRequestCode == 0) {
                PostInfo post = new PostInfo();
                post.setTitle(postTitle);
                post.setContent(postContent);
                post.setTag(postTag);
                post.setImg(photo);
                MyInfoManager.getInstance().addNewPost(post);
            }
            else if(targetRequestCode == 1){
                PostInfo post = MyInfoManager.getInstance().getEditPost();
                post.setTitle(postTitle);
                post.setContent(postContent);
                post.setTag(postTag);
                post.setImg(photo);
                MyInfoManager.getInstance().updatePost(post);
            }

            dismiss();

            callback.saveButtonOnClicked();
        }
    };

    private View.OnClickListener takePhotoListiner = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


            startActivityForResult(intent,CONTENTT_REQUEST);

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONTENTT_REQUEST ) {

            Bundle extras = data.getExtras();
            Bitmap bmp = (Bitmap) extras.get("data");
            postPhoto.setImageBitmap(bmp);


        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();
        View rootview = inflater.inflate(R.layout.fragment_edit_post_info, container, false);
        callback = (CallBackListiner) getTargetFragment();
        targetRequestCode = getTargetRequestCode();

        titleField = (EditText) rootview.findViewById(R.id.post_title_field);
        contentField = (EditText) rootview.findViewById(R.id.post_content_field);
        postPhoto = (ImageView) rootview.findViewById(R.id.post_img_field);
        takePhoto = (Button) rootview.findViewById(R.id.post_take_photo_btn);

        saveBtn = (Button) rootview.findViewById(R.id.save_post_btn);
        spinner = (Spinner) rootview.findViewById(R.id.post_tag_spinner);

        adapter = ArrayAdapter.createFromResource(context, R.array.tags,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        saveBtn.setOnClickListener(savePostListener);
        takePhoto.setOnClickListener(takePhotoListiner);


        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        int w = ViewGroup.LayoutParams.MATCH_PARENT;
        int h = ViewGroup.LayoutParams.MATCH_PARENT;

        d.getWindow().setLayout(w,h);
    }


}
