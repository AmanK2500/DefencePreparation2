package exam.defencepreparation.SSB;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;
import exam.defencepreparation.Login_SetUp.Login_activity;
import exam.defencepreparation.Login_SetUp.My_Profile_setting;
import exam.defencepreparation.Login_SetUp.Update_Status;
import exam.defencepreparation.R;
import exam.defencepreparation.news.NewsDetail;
import static exam.defencepreparation.R.layout.statescontain;




public class PPDT  extends Fragment {
    AdView mAdView;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    TextView read;
    AlertDialog dialog;

    public PPDT() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_airforce, container, false);

        dialog = new SpotsDialog(getActivity());
        dialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("PPDT");
        mDatabase.keepSynced(true);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.rec_airforce);


        mRecyclerView.hasFixedSize();
        LinearLayoutManager  mLayoutManger = new LinearLayoutManager(this.getActivity());
        mLayoutManger.setReverseLayout(true);
        mLayoutManger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManger);

        //banner ads code here


        mAdView = (AdView)view.findViewById(R.id.adView);



        AdRequest adRequest1 = new AdRequest.Builder()
                // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getActivity(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Toast.makeText(getActivity(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                //  Toast.makeText(getActivity(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest1);


        return view;

    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();





    }



    @Override
    public void onStart() {
        super.onStart();
        //progressBar.setVisibility(VISIBLE);


        FirebaseRecyclerAdapter<NewsDetail, PPDT.MyViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<NewsDetail, PPDT.MyViewHolder>
                (NewsDetail.class , statescontain, PPDT.MyViewHolder.class,mDatabase) {

            @Override
            protected void populateViewHolder(PPDT.MyViewHolder viewHolder, final NewsDetail model, int position) {

                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());
                final String user_id = getRef(position).getKey();

                dialog.dismiss();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent status_intent = new Intent(getActivity(), read_story.class);
                        status_intent.putExtra("post_id", user_id);
                        startActivity(status_intent);


                    }
                });


            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView post_desc;
        View mView;
        Button read,write;
        public MyViewHolder(View itemView)


        {
            super(itemView);
            mView=itemView;

        }



        public void setImage(final Context ctx, final String image){
            final ImageView post_image=(ImageView) mView.findViewById(R.id.new_pic);
            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.loadingpic).into(post_image, new Callback() {
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


