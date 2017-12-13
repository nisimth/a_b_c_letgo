package course.android.letgo_302838271_305212946;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.Locale;

import course.android.letgo_302838271_305212946.database.MyInfoManager;


public class MainActivity extends Activity {



    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyInfoManager.getInstance().openDataBase(this);

        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment home = new HomeFragment();
        ft.replace(R.id.root_view, home );
        ft.addToBackStack(null);
        ft.commit();

    }
    @Override
    protected void onResume() {
        super.onResume();
        MyInfoManager.getInstance().openDataBase(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyInfoManager.getInstance().closeDataBase();
    }

    public void homeOnClick(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment home = new HomeFragment();
        ft.add(R.id.root_view,home);
        ft.commit();
    }


}
