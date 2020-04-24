package exam.defencepreparation.Login_SetUp;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import exam.defencepreparation.R;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class View_other_user_Profile extends AppCompatActivity {
    private CircleImageView mDisplayImage;
    private TextView mName, email,phone,city;
    private TextView mStatus;
    private Uri mainImageURI = null;
    ImageView imageView;
    private DatabaseReference mUserDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_user__profile);
        final String user_id = getIntent().getStringExtra("user_id");

        mDisplayImage = (CircleImageView) findViewById(R.id.settings_image);
        imageView = (ImageView) findViewById(R.id.back);

        mName = (TextView) findViewById(R.id.settings_name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.mobile);
        city = (TextView) findViewById(R.id.address);

        mStatus = (TextView) findViewById(R.id.settings_status);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mUserDatabase.keepSynced(true);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                final String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String user_email = dataSnapshot.child("email").getValue().toString();
                String mobile = dataSnapshot.child("mobile").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                mainImageURI = Uri.parse(image);
                mName.setText(name);
                mStatus.setText(status);
                email.setText(user_email);
                phone.setText(mobile);
                city.setText(address);


                if(!image.equals("default")) {
                    Picasso.with(View_other_user_Profile.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.defence_logo_crop).into(mDisplayImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }
                        @Override
                        public void onError() {

                            Picasso.with(View_other_user_Profile.this).load(image).placeholder(R.drawable.defence_logo_crop).into(mDisplayImage);

                        }
                    });

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
