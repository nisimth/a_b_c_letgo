package course.android.letgo_302838271_305212946.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import course.android.letgo_302838271_305212946.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraOrGalleryDialogFragment extends DialogFragment {

    private ImageButton chooseFromGallery;
    private ImageButton chooseFromCamera;
    private Context context ;

    public CameraOrGalleryDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View rootView =  inflater.inflate(R.layout.fragment_camera_or_gallery_dialog, container, false);

        chooseFromGallery = (ImageButton)rootView.findViewById(R.id.galleryBtn);
        chooseFromCamera = (ImageButton)rootView.findViewById(R.id.mazlemaBtn);

        return rootView;
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
