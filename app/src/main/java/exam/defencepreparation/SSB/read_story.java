package exam.defencepreparation.SSB;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import exam.defencepreparation.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static exam.defencepreparation.R.layout.read_story;




public class read_story extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    String ppdt_image;
    ImageView imageView, imageView2;
    String ppdt_img_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_recycler_view);

        ppdt_img_id = getIntent().getStringExtra("post_id");
        ppdt_image = getIntent().getStringExtra("image");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("PPDT").child(ppdt_img_id).child("story");
        mDatabase.keepSynced(true);

        imageView=(ImageView) findViewById(R.id.add_story);
        imageView2=(ImageView) findViewById(R.id.image);


        //*************************Image View Code Start ***********************************

        Picasso.with(getApplicationContext()).load(ppdt_image).placeholder(R.color.cardview_dark_background).fit().centerCrop().into(imageView2, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

//***********************************Image View Code OVER________________***********************************


        mRecyclerView=(RecyclerView)findViewById(R.id.rec_story);
        mRecyclerView.hasFixedSize();
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(this);
        mLayoutManger.setReverseLayout(true);
        mLayoutManger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManger);

               imageView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       Intent status_intent = new Intent(exam.defencepreparation.SSB.read_story.this, ppdt_story.class);

                       status_intent.putExtra("post_id", ppdt_img_id);

                       startActivity(status_intent);

                   }
               });

    }


    @Override
    public void onStart() {
        super.onStart();
        //progressBar.setVisibility(VISIBLE);


        FirebaseRecyclerAdapter<story_model, read_story.MyViewHolder> firebaseRecyclerAdapter=new
                FirebaseRecyclerAdapter<story_model, read_story.MyViewHolder>
                (story_model.class ,read_story, read_story.MyViewHolder.class,mDatabase) {

            @Override
            protected void populateViewHolder(read_story.MyViewHolder viewHolder, final story_model model, int position) {

                viewHolder.setID(model.getID());
                viewHolder.setStory(model.getStory());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String story="";
                        String email="";
                        email=model.getID();
                        story=model.getStory();

                   Toast.makeText(exam.defencepreparation.SSB.read_story.this, "Running code", Toast.LENGTH_SHORT).show();

                   Intent status_intent = new Intent(exam.defencepreparation.SSB.read_story.this, Read_Full_Story.class);
                        status_intent.putExtra("story",story);
                        status_intent.putExtra("email", email);
                        status_intent.putExtra("image", ppdt_image);

                        startActivity(status_intent);


                    }
                });






            }
        };
        firebaseRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView post_desc;
        View mView;
        public MyViewHolder(View itemView)


        {
            super(itemView);
            mView=itemView;

        }

        public void setID(String ID)
        {
            TextView Topic=(TextView)mView.findViewById(R.id.news_title);
            Topic.setText(ID);
        }

        public void setStory(String Story){
            post_desc = (TextView)mView.findViewById(R.id.news_desc);
            post_desc.setText(Story);
        }





    }




}
