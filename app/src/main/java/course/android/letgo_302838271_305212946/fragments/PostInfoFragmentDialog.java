package course.android.letgo_302838271_305212946.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import course.android.letgo_302838271_305212946.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostInfoFragmentDialog extends DialogFragment {
    private TextView nameTxt;
    private ImageView img;
    private Context context;
    private TextView cashAmount ;
    private TextView currency ;
    private TextView description ;



    public PostInfoFragmentDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_post_info_fragment_dialog, container, false);

        //nameTxt = (TextView) rootView.findViewById(R.id.postinfodialog_itemname);
        img = (ImageView) rootView.findViewById(R.id.post_info_img);
        cashAmount = (TextView) rootView.findViewById(R.id.cash_amount_text_view);
        currency = (TextView) rootView.findViewById(R.id.currency_text_view);
        description = (TextView) rootView.findViewById(R.id.post_info_content_text_view);
        //exit = (ImageButton) rootView.findViewById(R.id.postinfodialog_exit_btn);
        //like = (ImageButton) rootView.findViewById(R.id.postinfodialog_addtofavorites_btn);

        //exit.setOnClickListener(exitOnClickListener);

        // recieve data
        //String name = this.getArguments().getString("NAME_KEY");
        String amount = this.getArguments().getString("AMOUNT_KEY");
        String currency1 = this.getArguments().getString("CURRENCY_KEY");
        String desc = this.getArguments().getString("DESC_KEY");
        byte[] image = this.getArguments().getByteArray("IMAGE_KEY");

        // bind data
        //nameTxt.setText(name);
        cashAmount.setText(amount);
        currency.setText(currency1);
        description.setText(desc);
        img.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));





        return rootView;
    }


    // exit from post info dialog
    private View.OnClickListener exitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            getTargetFragment();
        }
    };
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        int w = ViewGroup.LayoutParams.MATCH_PARENT;
        int h = ViewGroup.LayoutParams.MATCH_PARENT;

        d.getWindow().setLayout(w,h);
    }
}
