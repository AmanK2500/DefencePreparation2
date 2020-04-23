package exam.defencepreparation.Login_SetUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import exam.defencepreparation.R;
import exam.defencepreparation.Recycler_View_Click;
import exam.defencepreparation.defence_blog.Airforce;
import exam.defencepreparation.news.NewsDetail;

import static exam.defencepreparation.R.layout.interface_news;
import static exam.defencepreparation.R.layout.users_single_layout;

/**
 * A simple {@link Fragment} subclass.
 */
public class All_User_List extends Fragment {

    private DatabaseReference mUsersDatabase;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    public All_User_List() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_airforce, container, false);
     //   mDatabase = FirebaseDatabase.getInstance().getReference().child("Navy_blog");

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

       // mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView=(RecyclerView)view.findViewById(R.id.rec_airforce);
        mRecyclerView.hasFixedSize();
        LinearLayoutManager  mLayoutManger = new LinearLayoutManager(this.getActivity());
        mLayoutManger.setReverseLayout(true);
        mLayoutManger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManger);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        //progressBar.setVisibility(VISIBLE);


        FirebaseRecyclerAdapter<Users, MyViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Users, MyViewHolder>
                (Users.class , users_single_layout,MyViewHolder.class,mUsersDatabase) {

            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, Users model, int position) {


                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());



                final String user_id = getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent profileIntent = new Intent(getActivity(), View_other_user_Profile    .class);
                        profileIntent.putExtra("user_id", user_id);
                        startActivity(profileIntent);


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

        public void setName(String name)
        {
            TextView Topic=(TextView)mView.findViewById(R.id.user_single_name);
            Topic.setText(name);
        }

        public void setStatus(String status){
            post_desc = (TextView)mView.findViewById(R.id.user_single_status);
            post_desc.setText(status);
        }



        public void setImage(final Context ctx, final String image){
            final CircleImageView post_image=(CircleImageView) mView.findViewById(R.id.user_single_image);
            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.defencelogocrop).into(post_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).placeholder(R.drawable.loadingpic).into(post_image);

                }
            });
        }


    }


}

