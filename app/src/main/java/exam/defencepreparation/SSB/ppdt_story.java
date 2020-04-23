package exam.defencepreparation.SSB;


import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;
import exam.defencepreparation.R;
import io.reactivex.annotations.NonNull;

public class ppdt_story extends AppCompatActivity {
    private DatabaseReference mStatusDatabase,mDatabase;
    private FirebaseUser mCurrentUser;
    EditText editText;
    Button button;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ppdt_story);
        editText=findViewById(R.id.ppdtstory);
        button=findViewById(R.id.submit);
        dialog = new SpotsDialog(this);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getEmail();

      //  mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        String ppdt_img_id = getIntent().getStringExtra("post_id");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("PPDT").child(ppdt_img_id).child("story");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();

            }

            private void startPosting() {

                final String my_story=editText.getText().toString().trim();

                if(!TextUtils.isEmpty(my_story)) {
                    dialog.show();
                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("ID").setValue(current_uid);
                    newPost.child("Story").setValue(my_story);
                    Toast.makeText(ppdt_story.this, "Uploaded", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    finish();
                }

                else {
                    Toast.makeText(ppdt_story.this, "Write Your Story First ", Toast.LENGTH_LONG).show();

                }






            }
        });


    }
}
