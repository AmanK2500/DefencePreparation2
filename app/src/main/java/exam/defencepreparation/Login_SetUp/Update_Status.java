package exam.defencepreparation.Login_SetUp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import exam.defencepreparation.R;
import io.reactivex.annotations.NonNull;

public class Update_Status extends AppCompatActivity {

    private TextInputLayout  mStatus, Mobile,City;
    private Button mSavebtn;
    //Firebase
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;
    //Progress
    private ProgressDialog mProgress;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__status);

        //Firebase user
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        String status_value = getIntent().getStringExtra("status_value");
        String mobile = getIntent().getStringExtra("mobile");
        String city = getIntent().getStringExtra("city");



        mStatus = (TextInputLayout) findViewById(R.id.status_input);
        Mobile = (TextInputLayout) findViewById(R.id.mobile);
        City = (TextInputLayout) findViewById(R.id.city);
        mSavebtn = (Button) findViewById(R.id.status_save_btn);

        mStatus.getEditText().setText(status_value);
        Mobile.getEditText().setText(mobile);
        City.getEditText().setText(city);

        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Progress
                mProgress = new ProgressDialog(Update_Status.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();

                String status = mStatus.getEditText().getText().toString();
                String mobile = Mobile.getEditText().getText().toString();
                String city = City.getEditText().getText().toString();


                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(), "Status update Successfully.", Toast.LENGTH_LONG).show();
                           finish();

                        } else {

                            Toast.makeText(getApplicationContext(), "Filled Empty", Toast.LENGTH_LONG).show();

                        }  }});
                mStatusDatabase.child("mobile").setValue(mobile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(), "Status update Successfully.", Toast.LENGTH_LONG).show();
                            finish();

                        } else {

                            Toast.makeText(getApplicationContext(), "Filled Empty", Toast.LENGTH_LONG).show();

                        }  }});
                mStatusDatabase.child("address").setValue(city).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(), "Status update Successfully.", Toast.LENGTH_LONG).show();
                            finish();

                        } else {

                            Toast.makeText(getApplicationContext(), "Filled Empty", Toast.LENGTH_LONG).show();

                        }  }});

            }
        });


    }
}
