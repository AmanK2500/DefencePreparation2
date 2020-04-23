package exam.defencepreparation.defence_blog;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;
import exam.defencepreparation.R;
import exam.defencepreparation.Recycler_View_Click;
import exam.defencepreparation.news.NewsDetail;
import io.reactivex.annotations.NonNull;

import static exam.defencepreparation.R.layout.interface_news;



public class Army extends Fragment {
    AdView mAdView;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase,ndatabaseReference;
    TextView read;
    AlertDialog dialog;

    public Army() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_airforce, container, false);

        dialog = new SpotsDialog(getActivity());
        dialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("defence").child("Army_blog");
        mDatabase.keepSynced(true);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.rec_airforce);

        mRecyclerView.hasFixedSize();
        LinearLayoutManager  mLayoutManger = new LinearLayoutManager(this.getActivity());
        mLayoutManger.setReverseLayout(true);
        mLayoutManger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManger);

        //banner ads code here


        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
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


        FirebaseRecyclerAdapter<NewsDetail, Army.MyViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<NewsDetail, Army.MyViewHolder>
                (NewsDetail.class , interface_news, Army.MyViewHolder.class,mDatabase) {

            @Override
            protected void populateViewHolder(Army.MyViewHolder viewHolder, final NewsDetail model, int position) {

                viewHolder.setTopic(model.getTopic());
                viewHolder.setDetail(model.getDetail());
                viewHolder.setDate(model.getDate());
                viewHolder.setView(model.getView());
                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());
                final    String user_id = getRef(position).getKey();

                dialog.dismiss();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String topic="";
                        String detail="";
                        String date="";
                        String image="";
                        String view="";
                        String link1="defence";
                        String link="Army_blog";

                        topic=model.getTopic();
                        detail=model.getDetail();
                        date=model.getDate();
                        image=model.getImage();
                        view=model.getView();

                        ndatabaseReference= FirebaseDatabase.getInstance().getReference().child("defence").child("Army_blog").child(user_id);

                        if(view==null)
                        {
                            int new_view_value = 0;
                            int increase_view = new_view_value +1;
                            String updated_view = String.valueOf(increase_view);

                            ndatabaseReference.child("view").setValue(updated_view).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        Toast.makeText(getActivity(), "Enjoy Learning.", Toast.LENGTH_LONG).show();


                                    } else {

                                        Toast.makeText(getActivity(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                    }

                                }
                            });

                        }

                        else {
                            int view_count = Integer.parseInt(view);
                            int increase_view = view_count + 1;
                            String updated_view = String.valueOf(increase_view);
                            ndatabaseReference.child("view").setValue(updated_view).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(getActivity(), "Enjoy Learning.", Toast.LENGTH_LONG).show();


                                    } else {

                                        Toast.makeText(getActivity(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                    }

                                }
                            });

                        }



                        Intent imgFullScrn = new Intent(getActivity(), Recycler_View_Click.class);
                        imgFullScrn.putExtra("topic",topic);
                        imgFullScrn.putExtra("detail",detail);
                        imgFullScrn.putExtra("date",date);
                        imgFullScrn.putExtra("image",image);
                        imgFullScrn.putExtra("datalink",link);
                        imgFullScrn.putExtra("defence",link1);


                        startActivity(imgFullScrn);
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

        public void setTopic(String topic)
        {
            TextView Topic=(TextView)mView.findViewById(R.id.news_title);
            Topic.setText(topic);
        }

        public void setDetail(String detail){
            post_desc = (TextView)mView.findViewById(R.id.news_desc);
            post_desc.setText(detail);
        }

        public void setDate(String date){
            TextView  Date = (TextView)mView.findViewById(R.id.date);
            Date.setText(date);
        }
        public void setView(String view){
            TextView  showView = (TextView)mView.findViewById(R.id.view);
            showView.setText(view);
        }

        public void setImage(final Context ctx, final String image){
            final KenBurnsView post_image=(KenBurnsView) mView.findViewById(R.id.new_pic);
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

