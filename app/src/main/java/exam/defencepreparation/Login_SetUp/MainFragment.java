package exam.defencepreparation.Login_SetUp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import exam.defencepreparation.CAPF_AC_2020.Capf_ac_2020_home;
import exam.defencepreparation.E_Books.E_Books_Main;
import exam.defencepreparation.Previous_year_paper.English_Section;
import exam.defencepreparation.Quiz.Home;
import exam.defencepreparation.R;
import exam.defencepreparation.Rec_htmlView;
import exam.defencepreparation.SSB.SSB_Final;
import exam.defencepreparation.Youtube_general_study.YouTube_general;
import exam.defencepreparation.defence_blog.Defence_Blog;
import exam.defencepreparation.news.Airforce;
import exam.defencepreparation.news.NewsDetail;
import exam.defencepreparation.news.News_Daily;

import static exam.defencepreparation.R.layout.rec_horigental_design;
import static exam.defencepreparation.R.layout.youtube_rec_design;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private StorageReference mImageStorage;
    private Uri mainImageURI = null;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private CircleImageView mDisplayImage;
    private TextView mName;

    CardView mycard1, mycard2, mycard3, mycard4, mycard5, mycard6,mycard7,mycard8;
    Intent i, ii, iii, iiii, iiiii, VI,VII,VIII;
    LinearLayout ll;
    Dialog myDialog;
    AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trydashboard, container, false);
        mycard1 = (CardView) view.findViewById(R.id.bankcardId);
        mycard2 = (CardView) view.findViewById(R.id.defenceinside);
        mycard3 = (CardView) view.findViewById(R.id.ssb);
        mycard4 = (CardView) view.findViewById(R.id.video);
        mycard5 = (CardView) view.findViewById(R.id.general);
        mycard6 = (CardView) view.findViewById(R.id.previous_year);
        mycard7 = (CardView) view.findViewById(R.id.e_books);
        mycard8 = (CardView) view.findViewById(R.id.quiz);
        mAdView = (AdView)view.findViewById(R.id.adView);



        mDisplayImage=(CircleImageView)view.findViewById(R.id.imageView2);
        mName=(TextView) view.findViewById(R.id.nameuser);
        AdRequest adsRequest = new AdRequest.Builder().build();


        // recyclerview code----------------
        mDatabase = FirebaseDatabase.getInstance().getReference("National_news");
        mDatabase.keepSynced(true);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.front_page_recycler_view);
        mRecyclerView.hasFixedSize();
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,true);
        mLayoutManger.setReverseLayout(true);
        mLayoutManger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManger);





        i = new Intent(getActivity(), News_Daily.class);
        ii = new Intent(getActivity(), Defence_Blog.class);
        iii = new Intent(getActivity(), SSB_Final.class);
        iiii = new Intent(getActivity(), Capf_ac_2020_home.class);


        mycard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        mycard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ii);
            }
        });
        mycard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iii);
            }
        });
        mycard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iiii);
            }
        });
        mycard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iiiii);
            }
        });
        mycard6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VI);
            }
        });
        mycard7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VII);
            }
        });
        mycard8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VIII);
            }
        });




        iiiii= new Intent(getActivity(), YouTube_general.class);
        VI = new Intent(getActivity(), English_Section.class);
        VII = new Intent(getActivity(), E_Books_Main.class);
        VIII = new Intent(getActivity(), Home.class);






        // ads coding




        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                //  Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adsRequest);


        // fetching name and pic of the particular user


        mImageStorage = FirebaseStorage.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();


        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.keepSynced(true);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                final String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                mainImageURI = Uri.parse(image);
                mName.setText(name);
               // mStatus.setText(status);
                if(!image.equals("default")) {
                    Picasso.with(getActivity()).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.defence_logo_crop).into(mDisplayImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }
                        @Override
                        public void onError() {

                            Picasso.with(getActivity()).load(image).placeholder(R.drawable.defence_logo_crop).into(mDisplayImage);

                        }
                    });

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return view;
    }
    // banner ads


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
        FirebaseRecyclerAdapter<NewsDetail, MainFragment.MyViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<NewsDetail, MainFragment.MyViewHolder>
                (NewsDetail.class , rec_horigental_design, MainFragment.MyViewHolder.class,mDatabase) {

            @Override
            protected void populateViewHolder(MainFragment.MyViewHolder viewHolder, final NewsDetail model, int position) {


                viewHolder.setTopic(model.getTopic());
                viewHolder.setDetail(model.getDetail());
                viewHolder.setDate(model.getDate());
                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String topic="";
                        String detail="";
                        String date="";
                        String image="";
                        String link="National_news";

                        topic=model.getTopic();
                        detail=model.getDetail();
                        date=model.getDate();
                        image=model.getImage();


                        Intent imgFullScrn = new Intent(getActivity(), Rec_htmlView.class);
                        imgFullScrn.putExtra("topic",topic);
                        imgFullScrn.putExtra("detail",detail);
                        imgFullScrn.putExtra("date",date);
                        imgFullScrn.putExtra("image",image);
                        imgFullScrn.putExtra("datalink",link);


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
            TextView Topic=(TextView)mView.findViewById(R.id.tittle);
            Topic.setText(topic);
        }

        public void setDetail(String detail){
            post_desc = (TextView)mView.findViewById(R.id.topic1);
            post_desc.setText(Html.fromHtml(detail));        }

        public void setDate(String date){
            TextView  Date = (TextView)mView.findViewById(R.id.time);
            Date.setText(date);
        }

        public void setImage(final Context ctx, final String image){
            final ImageView post_image=(ImageView) mView.findViewById(R.id.card_image);
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

