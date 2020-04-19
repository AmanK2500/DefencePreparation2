package exam.defencepreparation.SSB;


import android.os.Bundle;
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
import exam.defencepreparation.R;
import io.reactivex.annotations.NonNull;

public class ppdt_story extends AppCompatActivity {
    private DatabaseReference mStatusDatabase,mDatabase;
    private FirebaseUser mCurrentUser;
    EditText editText;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ppdt_story);
        editText=findViewById(R.id.ppdtstory);
        button=findViewById(R.id.submit);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();

        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        String ppdt_img_id = getIntent().getStringExtra("post_id");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("PPDT").child(ppdt_img_id).child("story");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }

            private void startPosting() {

                final String my_story=editText.getText().toString().trim();

                DatabaseReference newPost =mDatabase.push();
                newPost.child("ID").setValue(current_uid);
                newPost.child("Story").setValue(my_story);



            }
        });


    }
}
