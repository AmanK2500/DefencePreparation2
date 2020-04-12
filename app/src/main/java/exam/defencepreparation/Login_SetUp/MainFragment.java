package exam.defencepreparation.Login_SetUp;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import de.hdodenhof.circleimageview.CircleImageView;
import exam.defencepreparation.CAPF_AC_2020.Capf_ac_2020_home;
import exam.defencepreparation.R;
import exam.defencepreparation.SSB.SSB_Final;
import exam.defencepreparation.Static_GK.DashBoard_Video;
import exam.defencepreparation.defence_blog.Defence_Blog;
import exam.defencepreparation.news.News_Daily;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private StorageReference mImageStorage;
    private Uri mainImageURI = null;

    private CircleImageView mDisplayImage;
    private TextView mName;

    CardView mycard1, mycard2, mycard3, mycard4, mycard5;
    Intent i, ii, iii, iiii, iiiii;
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
        mycard5 = (CardView) view.findViewById(R.id.shares);
        mAdView = (AdView)view.findViewById(R.id.adView);

        mDisplayImage=(CircleImageView)view.findViewById(R.id.imageView2);
        mName=(TextView) view.findViewById(R.id.nameuser);


        AdRequest adsRequest = new AdRequest.Builder().build();

        i = new Intent(getActivity(), News_Daily.class);
        ii = new Intent(getActivity(), Defence_Blog.class);
        iii = new Intent(getActivity(), SSB_Final.class);
        iiii = new Intent(getActivity(), Capf_ac_2020_home.class);
        iiiii = new Intent(getActivity(), DashBoard_Video.class);


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



}

