package course.android.letgo_302838271_305212946;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import course.android.letgo_302838271_305212946.database.MyInfoManager;
import course.android.letgo_302838271_305212946.fragments.ChatsFragment;
import course.android.letgo_302838271_305212946.fragments.HomeFragment;
import course.android.letgo_302838271_305212946.fragments.MyProfileFragment;
import course.android.letgo_302838271_305212946.fragments.NotificatiosFragment;
import course.android.letgo_302838271_305212946.network.utils.NetworkConnector;


public class MainActivity extends Activity {


    private Context context ;
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyInfoManager.getInstance().openDataBase(this);
        NetworkConnector.getInstance().initialize(this);

        hideKeyboard();

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
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment home = new HomeFragment();
        ft.replace(R.id.root_view, home);
        ft.addToBackStack(null);
        ft.commit();


    }
    public void notificationsOnClick(View view) {
        FragmentTransaction ft = fm.beginTransaction();
        NotificatiosFragment notificatiosFragment = new NotificatiosFragment();
        ft.replace(R.id.root_view, notificatiosFragment);
        ft.addToBackStack(null);
        ft.commit();
    }



    public void chatOnClick(View view) {
        FragmentTransaction ft = fm.beginTransaction();
        ChatsFragment chatsFragment = new ChatsFragment();
        ft.replace(R.id.root_view,chatsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void proflieOnClick(View view) {
        FragmentTransaction ft = fm.beginTransaction();
        MyProfileFragment profileFragment = new MyProfileFragment();
        ft.replace(R.id.root_view,profileFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void hideKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}

