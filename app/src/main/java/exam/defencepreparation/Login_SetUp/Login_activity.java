package exam.defencepreparation.Login_SetUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import androidx.appcompat.app.AppCompatActivity;
import exam.defencepreparation.R;
import io.reactivex.annotations.NonNull;

public class Login_activity extends AppCompatActivity {
    EditText email,password;
    FirebaseAuth auth;
    Button button, forget;
    ImageView loginimageView;
    TextView forget1,signup;
    private FirebaseUser mCurrentUser;

    private ProgressDialog loadingbar;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        auth=FirebaseAuth.getInstance();

        mdatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        email=(EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);
        loginimageView=(ImageView) findViewById(R.id.login);
        forget1=(TextView)findViewById(R.id.forget);
        signup=(TextView) findViewById(R.id.signup);


        loadingbar = new ProgressDialog(this);

        forget1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new  Intent(Login_activity.this, Reset_Password.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new  Intent(Login_activity.this, Registration_Page.class);
                startActivity(i);
            }
        });


        loginimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress= email.getText().toString();
                String UserPassword = password.getText().toString();
                LoginInUserAccount(emailAddress ,UserPassword);
            }
        });


    }





    private void LoginInUserAccount(String emailAddress, String userPassword) {
        if(TextUtils.isEmpty(emailAddress))
        {

            Toast.makeText(Login_activity.this,"enter email",Toast.LENGTH_LONG).show();
        }

        else {

            loadingbar.setTitle("Login Account");
            loadingbar.setMessage("please wait .........");
            loadingbar.show();



            auth.signInWithEmailAndPassword(emailAddress , userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                //Log.w("TAG", "signInWithEmail:failed", task.getException());

                            } else {
                                checkIfEmailVerified();
                            }
                            // ...loadingbar.dismiss();
                        }
                    });
        }
        
    }

    private void checkIfEmailVerified() {

        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user.isEmailVerified()) {
                // user is verified, so you can finish this activity or send user to activity which you want.
                Intent i =new  Intent(Login_activity.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(Login_activity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            } else {
                // email is not verified, so just prompt the message to the user and restart this activity.
                // NOTE: don't forget to log out the user.
                Toast.makeText(Login_activity.this, "Email not verify ", Toast.LENGTH_LONG).show();

                FirebaseAuth.getInstance().signOut();


                //restart this activity

            }

        }
    }}
