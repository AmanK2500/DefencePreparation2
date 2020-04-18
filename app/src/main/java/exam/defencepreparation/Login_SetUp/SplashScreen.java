package exam.defencepreparation.Login_SetUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import exam.defencepreparation.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        Logo xyz = new Logo();
        xyz.start();

    }
    private class Logo extends Thread{
        public void run(){
            try{
                sleep(3000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            Intent intent = new Intent(SplashScreen.this, Login_activity.class);
            startActivity(intent);
            SplashScreen.this.finish();
        }
    }
}
