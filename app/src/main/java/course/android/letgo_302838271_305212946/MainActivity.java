package course.android.letgo_302838271_305212946;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import course.android.letgo_302838271_305212946.database.MyInfoManager;
import course.android.letgo_302838271_305212946.fragments.HomeFragment;
import course.android.letgo_302838271_305212946.fragments.MyProfileFragment;


public class MainActivity extends Activity {


    private Context context ;
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
        ft.replace(R.id.root_view, home);
        ft.addToBackStack(null);
        ft.commit();


    }


    public void proflieOnClick(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MyProfileFragment profileFragment = new MyProfileFragment();
        ft.replace(R.id.root_view,profileFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    /*private void hideKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }*/
}

