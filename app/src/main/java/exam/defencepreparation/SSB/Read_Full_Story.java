package exam.defencepreparation.SSB;

import androidx.appcompat.app.AppCompatActivity;
import exam.defencepreparation.R;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Read_Full_Story extends AppCompatActivity {

    ImageView imageView1;
    TextView user_email, user_story;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read__full__story);

        imageView1=findViewById(R.id.image);
        user_email=findViewById(R.id.user_email);
        user_story= findViewById(R.id.user_story);

        String story = getIntent().getStringExtra("story");
        String email = getIntent().getStringExtra("email");
        String imageView = getIntent().getStringExtra("image");


        user_email.setText(email);
        user_story.setText(story);
        Picasso
                .with(getApplicationContext())
                .load(imageView)
                .placeholder(R.color.cardview_dark_background)
                .fit()
                .centerCrop()
                .into(imageView1, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });



    }
}
