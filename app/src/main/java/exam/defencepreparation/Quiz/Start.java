package exam.defencepreparation.Quiz;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;
import exam.defencepreparation.Quiz.Common.Common;
import exam.defencepreparation.Quiz.Model.Question;
import exam.defencepreparation.R;

public class Start extends AppCompatActivity {

    Button btnPlay;
    AdView mAdView;

    FirebaseDatabase database;
    DatabaseReference questions;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        dialog = new SpotsDialog(this);
        dialog.show();


        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Question_QUIZ");

        loadQuestion(Common.categoryId);
        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start.this,Playing.class);
                startActivity(intent);
                finish();
            }
        });
        // ads coding

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adsRequest = new AdRequest.Builder()

                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                //Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adsRequest);
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

    private void loadQuestion(String categoryId) {

        if (Common.questionList.size() > 0){
            Common.questionList.clear();
        }
        questions.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                            Question ques = postSnapShot.getValue(Question.class);
                            Common.questionList.add(ques);
                            Collections.shuffle(Common.questionList);
                        }

                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //Collections.shuffle(Common.questionList);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
