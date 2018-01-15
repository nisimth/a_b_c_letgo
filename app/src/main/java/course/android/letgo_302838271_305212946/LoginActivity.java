package course.android.letgo_302838271_305212946;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import course.android.letgo_302838271_305212946.database.MyInfoManager;

public class LoginActivity extends AppCompatActivity {

    Button user1;
    Button user2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user1 = (Button) findViewById(R.id.user1_btn);
        user2 = (Button) findViewById(R.id.user2_btn);
    }

    public void user1OnClick(View view) {
        MyInfoManager.getInstance().setMyUserId("letgo");
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);


    }

    public void user2OnClick(View view) {
        MyInfoManager.getInstance().setMyUserId("letgo2");
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
