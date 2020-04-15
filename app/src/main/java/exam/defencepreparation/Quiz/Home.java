package exam.defencepreparation.Quiz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import exam.defencepreparation.Login_SetUp.MainFragment;
import exam.defencepreparation.R;
import exam.defencepreparation.Static_GK.Function;


public class Home extends AppCompatActivity {
    Dialog myDialog;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myDialog = new Dialog(this);

        setDefaultFragment();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Intent intent = new Intent(Home.this, MainFragment.class);
        //startActivity(intent);

        finish();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      //

        if (Function.isNetworkAvailable(this)) {

            transaction.replace(R.id.frame_layout, CategoryFragment.newInstance());
            transaction.commit();

        }
        //
         else {
            myDialog.setContentView(R.layout.custompopup);

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }

    }
}
