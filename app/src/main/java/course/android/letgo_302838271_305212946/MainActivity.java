package course.android.letgo_302838271_305212946;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.Locale;


public class MainActivity extends Activity {



    Button homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout f1 = (FrameLayout) findViewById(R.id.root_view);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment home = new HomeFragment();
        ft.add(R.id.root_view, home );
        ft.addToBackStack(null);
        ft.commit();

    }

    public void homeOnClick(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment home = new HomeFragment();
        ft.add(R.id.root_view,home);
        ft.commit();
    }
}
